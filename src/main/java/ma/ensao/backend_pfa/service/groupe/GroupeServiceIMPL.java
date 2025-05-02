package ma.ensao.backend_pfa.service.groupe;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import ma.ensao.backend_pfa.entity.Groupe;
import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.repository.GroupeRepository;
import ma.ensao.backend_pfa.repository.ProjectRepository;
import ma.ensao.backend_pfa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GroupeServiceIMPL implements GroupeService{
    public static int nbrdegrp;
    @Autowired
    private GroupeRepository grouperepository;
    private final UserRepository userRepository;
    private ProjectRepository projectRepository;

    public GroupeServiceIMPL(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectRepository = projectRepository;

    }

    @Override
    public List<Groupe> listAllGroupes() {
        return grouperepository.findAll();
    }

    @Override
    public void saveGroupe(Groupe groupe) {
        grouperepository.save(groupe);
        nbrdegrp++;
    }

    @Override
    public Groupe getGroupeById(long id) {
        return grouperepository.findById(id).get();
    }

    @Override
    public void deletegroupe(long id) {
        grouperepository.deleteById(id);
    }

    @Override
    public List<Groupe> listAllGroupesByProject(Project project) {
        return List.of();
    }

    @Override
    public void addProjectToGroupe(Groupe groupe, Project project) {
    }

    @Override
    public List<Project> listAllProjectsByGroupe(Groupe groupe) {
        return List.of();
    }


    @Override
    public void invitestudentsToGroupe(Groupe groupe, List<String> email) {
        email.forEach(e -> {
            User user = userRepository.findByEmail(e)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + e));
            groupe.getUsers().add(user);
            user.setGroupe(groupe);
            if (!groupe.getUsers().contains(user)) {
                groupe.getUsers().add(user);
            }

            // Save user explicitly
            userRepository.save(user);
        });

        grouperepository.save(groupe);
    }
    @Override
    @Transactional
    public void removeStudentFromGroupe(Groupe groupe, String email) {
        User userToRemove = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if (groupe.getUsers().contains(userToRemove)) {
            // Check if the user being removed is the representant
            if (userToRemove.equals(groupe.getRepresentant())) {
                // If the representant is removed, delete the entire group
                grouperepository.delete(groupe);
            } else {
                // If it's not the representant, simply remove the user from the group
                groupe.getUsers().remove(userToRemove);
                userToRemove.setGroupe(null); // Important: Disassociate the user from the group
                userRepository.save(userToRemove);
                grouperepository.save(groupe);
            }
        }
    }
}
