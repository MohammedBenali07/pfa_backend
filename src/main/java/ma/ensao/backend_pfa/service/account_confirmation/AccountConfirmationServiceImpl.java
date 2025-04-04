package ma.ensao.backend_pfa.service.account_confirmation;

import lombok.RequiredArgsConstructor;
import ma.ensao.backend_pfa.entity.AccountConfirmation;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.repository.AccountConfirmationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountConfirmationServiceImpl implements AccountConfirmationService {

    private final AccountConfirmationRepository confirmationRepository;

    @Override
    public String createConfirmationToken(User user) {
        String token = UUID.randomUUID().toString();
        AccountConfirmation confirmation = new AccountConfirmation();
        confirmation.setToken(token);
        confirmation.setUser(user);
        confirmation.setExpiryDate(LocalDateTime.now().plusHours(24));
        confirmation.setConfirmed(false);
        confirmationRepository.save(confirmation);
        return token;
    }

    @Override
    public Optional<AccountConfirmation> getByToken(String token) {
        return confirmationRepository.findByToken(token);
    }

    @Override
    public void confirmToken(String token) {
        AccountConfirmation confirmation = confirmationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide ou inexistant"));

        if (confirmation.isConfirmed()) {
            throw new RuntimeException("Le compte est déjà confirmé");
        }

        if (confirmation.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Le lien de confirmation a expiré");
        }

        confirmation.setConfirmed(true);
        confirmation.getUser().setEnabled(true); // il faut un champ enabled dans User
        confirmationRepository.save(confirmation);
    }
}