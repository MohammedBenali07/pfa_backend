package ma.ensao.backend_pfa.repository;

import ma.ensao.backend_pfa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name); 
}
