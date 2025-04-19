package ma.ensao.backend_pfa.service.auth;

import lombok.RequiredArgsConstructor;
import ma.ensao.backend_pfa.dto.AuthRequest;
import ma.ensao.backend_pfa.dto.RegisterRequest;
import ma.ensao.backend_pfa.dto.VerifyUserDto;
import ma.ensao.backend_pfa.entity.AccountConfirmation;
import ma.ensao.backend_pfa.entity.Role;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.repository.AccountConfirmationRepository;
import ma.ensao.backend_pfa.repository.RoleRepository;
import ma.ensao.backend_pfa.repository.UserRepository;
import ma.ensao.backend_pfa.security.JwtUtil;
import ma.ensao.backend_pfa.service.user.UserService;
import ma.ensao.backend_pfa.util.EmailUtil;
import ma.ensao.backend_pfa.util.GenerateCodeUtil;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AccountConfirmationRepository accountConfirmationRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager; 
    private final GenerateCodeUtil generateCodeUtil;
    private final EmailUtil emailUtil;

    @Override
    public String login(AuthRequest authRequest) {
    	System.out.println("1");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        System.out.println("2");
        User user = userService.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        System.out.println("3");
        return jwtUtil.generateToken(user.getEmail(),user.getRole());
    }
   

    @Override
    @Transactional
    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà.");
        }

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCin(registerRequest.getCin());
        user.setBranch(registerRequest.getBranch());
        user.setImageUrl(registerRequest.getImageUrl());
        user.setEnabled(false);

        Role role = entityManager.getReference(Role.class, registerRequest.getRoleId());
        user.setRole(role);

        User savedUser = userRepository.save(user);

        String token = UUID.randomUUID().toString();
        String verificationCode = generateCodeUtil.generateVerificationCode(); 
        AccountConfirmation confirmation = new AccountConfirmation(
                null,
                token,
                LocalDateTime.now().plusMinutes(30),
                savedUser,
                false,
                verificationCode,
                LocalDateTime.now().plusMinutes(15)
        );

        accountConfirmationRepository.save(confirmation);

        sendVerificationEmail(savedUser,verificationCode); 

        return savedUser;
    }


   

    public String verify(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        System.out.println("avant");
        if (authentication.isAuthenticated()) {
        	Role role = userRepository.findRoleByEmail(authRequest.getEmail());
        	System.out.println("apres");
            return jwtUtil.generateToken(authRequest.getEmail(),role);
        } else {
            throw new RuntimeException("Échec d'authentification.");
        }
    }
    public void verifyUser(VerifyUserDto verifyUserDto) {
        Optional<User> optionalUser = userRepository.findByEmail(verifyUserDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<AccountConfirmation> accountConfirmationOptional = accountConfirmationRepository.findByUser(user);
            if (accountConfirmationOptional.isPresent()) {
                AccountConfirmation accountConfirmation = accountConfirmationOptional.get();

                if (accountConfirmation.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                    throw new RuntimeException("Verification code has expired");
                }

                // Vérification du code de vérification
                if (accountConfirmation.getVerificationCode().equals(verifyUserDto.getVerificationCode())) {
                    // Confirmation du compte
                    accountConfirmation.setConfirmed(true);
                    accountConfirmationRepository.save(accountConfirmation);

                    // Activation de l'utilisateur
                    user.setEnabled(true);
                    userRepository.save(user);

                    // Réinitialisation du code de vérification
                    accountConfirmation.setVerificationCode(null);
                    accountConfirmation.setVerificationCodeExpiresAt(null);
                    accountConfirmationRepository.save(accountConfirmation);
                } else {
                    throw new RuntimeException("Invalid verification code");
                }
            } else {
                throw new RuntimeException("Account confirmation not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void sendVerificationEmail(User user,String verificationCode) { 
        String subject = "Account Verification";
        String verification = "VERIFICATION CODE " + verificationCode;
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Enter this 6-digit code to verify your identity:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verification + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailUtil.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }

            AccountConfirmation accountConfirmation = accountConfirmationRepository.findByUser(user)
                    .orElse(new AccountConfirmation(null, UUID.randomUUID().toString(), 
                            LocalDateTime.now().plusMinutes(30), user, false, 
                            generateCodeUtil.generateVerificationCode(), LocalDateTime.now().plusMinutes(15)));
            accountConfirmation.setVerificationCode(generateCodeUtil.generateVerificationCode());
            accountConfirmation.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            
            accountConfirmationRepository.save(accountConfirmation);
            sendVerificationEmail(user, accountConfirmation.getVerificationCode());
        } else {
            throw new RuntimeException("User not found");
        }
    }

}
