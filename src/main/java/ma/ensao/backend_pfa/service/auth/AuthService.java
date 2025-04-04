package ma.ensao.backend_pfa.service.auth;
import ma.ensao.backend_pfa.entity.User;

public interface AuthService {
    String login(String email, String password);
    String register(User user);
    void confirmAccount(String token);
}
