package ma.ensao.backend_pfa.repository;

import ma.ensao.backend_pfa.entity.AccountConfirmation;
import ma.ensao.backend_pfa.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountConfirmationRepository extends JpaRepository<AccountConfirmation, Long> {

    Optional<AccountConfirmation> findByToken(String token);
    Optional<AccountConfirmation> findByUserId(Long userId);
    Optional<AccountConfirmation> findByUser(User user);
    boolean existsByUserIdAndConfirmed(Long userId, boolean confirmed);
    // Supprimer les anciens tokens expirés si besoin (optionnel)
    void deleteByExpiryDateBefore(java.time.LocalDateTime dateTime);
}