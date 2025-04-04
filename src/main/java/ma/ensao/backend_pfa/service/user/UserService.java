package ma.ensao.backend_pfa.service.user;

import ma.ensao.backend_pfa.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User>  getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    User updateUser(Long id, User updatedUser);
    void deleteById(Long id);
}
