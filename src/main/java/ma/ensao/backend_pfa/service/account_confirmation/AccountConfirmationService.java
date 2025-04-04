package ma.ensao.backend_pfa.service.account_confirmation;

import ma.ensao.backend_pfa.entity.AccountConfirmation;
import ma.ensao.backend_pfa.entity.User;

import java.util.Optional;

public interface AccountConfirmationService {
    String createConfirmationToken(User user);
    Optional<AccountConfirmation> getByToken(String token);
    void confirmToken(String token);
}