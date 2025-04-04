package ma.ensao.backend_pfa.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import ma.ensao.backend_pfa.entity.AccountConfirmation;
import ma.ensao.backend_pfa.repository.AccountConfirmationRepository;
import ma.ensao.backend_pfa.repository.UserRepository;

@Service
public class AccountConfirmationService {

    private AccountConfirmationRepository accountConfirmationRepository;

    private UserRepository userRepository;

    private EmailService emailService;  

    public void sendConfirmationEmail(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24); 

        AccountConfirmation confirmation = new AccountConfirmation();
        confirmation.setToken(token);
        confirmation.setExpiryDate(expiryDate);
        confirmation.setUser(user);

        accountConfirmationRepository.save(confirmation);

        String confirmationLink = "https://ton-app.com/confirm?token=" + token;
        emailService.sendEmail(user.getEmail(), "Confirmez votre compte", "Cliquez sur ce lien pour activer votre compte : " + confirmationLink);
    }

    public boolean confirmAccount(String token) {
        AccountConfirmation confirmation = accountConfirmationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (confirmation.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Le token a expiré");
        }

        // Activation du compte
        User user = confirmation.getUser();
        user.setEnabled(true); // Activer l'utilisateur
        userRepository.save(user);

        confirmation.setConfirmed(true);
        accountConfirmationRepository.save(confirmation);

        return true; // Compte activé avec succès
    }
}
