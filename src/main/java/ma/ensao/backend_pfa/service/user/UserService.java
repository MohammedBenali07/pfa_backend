package ma.ensao.backend_pfa.service.user;

import ma.ensao.backend_pfa.entity.User;

import java.util.Optional;

public interface UserService {
    String registerUser(User user);
    void confirmUser(String token);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
