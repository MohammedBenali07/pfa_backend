package ma.ensao.backend_pfa.service.account_confirmation;

import ma.ensao.backend_pfa.entity.AccountConfirmation;
import ma.ensao.backend_pfa.entity.User;

import java.util.Optional;

import org.springframework.stereotype.Service;
@Service
public interface AccountConfirmationService {
    String createConfirmationToken(User user);
    Optional<AccountConfirmation> getByToken(String token);
    void confirmToken(String token);
}