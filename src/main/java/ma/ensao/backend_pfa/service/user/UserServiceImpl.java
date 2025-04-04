package ma.ensao.backend_pfa.service.user;


import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public User saveUser(User user) {
        return userRepository.save(user); 
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        
        updatedUser.setId(id);  // Assurez-vous que l'ID de l'utilisateur est correctement assigné

        return userRepository.save(updatedUser);  // Sauvegarde les modifications
    }
   

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

	
}

    
    
    
    
    
    
    
    
    
    
    
    
    /* @Override
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
    }*/


