package ma.ensao.backend_pfa.repository;

import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByUser(User user);
}
