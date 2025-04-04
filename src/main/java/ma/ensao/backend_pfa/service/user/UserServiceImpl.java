package ma.ensao.backend_pfa.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.ensao.backend_pfa.entity.AccountConfirmation;
import ma.ensao.backend_pfa.entity.Role;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.enums.RoleType;
import ma.ensao.backend_pfa.repository.AccountConfirmationRepository;
import ma.ensao.backend_pfa.repository.RoleRepository;
import ma.ensao.backend_pfa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountConfirmationRepository confirmationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role defaultRole = roleRepository.findByName(RoleType.STUDENT)
                .orElseThrow(() -> new RuntimeException("Le rôle par défaut est introuvable."));
        user.setRole(defaultRole);

        User savedUser = userRepository.save(user);

        String token = UUID.randomUUID().toString();
        AccountConfirmation confirmation = new AccountConfirmation(
                null,
                token,
                LocalDateTime.now().plusMinutes(30),
                savedUser,
                false
        );
        confirmationRepository.save(confirmation);

        return token;
    }

    @Override
    @Transactional
    public void confirmUser(String token) {
        AccountConfirmation confirmation = confirmationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token de confirmation invalide."));

        if (confirmation.isConfirmed()) {
            throw new RuntimeException("Ce compte est déjà confirmé.");
        }

        if (confirmation.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Le token a expiré.");
        }

        confirmation.setConfirmed(true);
        confirmationRepository.save(confirmation);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
