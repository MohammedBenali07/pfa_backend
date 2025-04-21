package ma.ensao.backend_pfa.repository;
import ma.ensao.backend_pfa.entity.Groupe;
import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.entity.Role;
import ma.ensao.backend_pfa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    Optional<Groupe> findBy(String representant);
    boolean existsBy(String representant);

    List<Groupe> findByProject(Project project);
}
