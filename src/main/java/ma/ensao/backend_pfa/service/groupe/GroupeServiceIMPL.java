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

import java.util.UUID;

private final EmailService emailService;
import java.util.List;
@Service
public class GroupeServiceIMPL implements GroupeService{
    @Autowired
    private GroupeRepository grouperepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public GroupeServiceIMPL(UserRepository userRepository, ProjectRepository projectRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.emailService = emailService;
    }

    @Override
    public List<Groupe> listAllGroupes() {
        return grouperepository.findAll();
    }

    @Override
    public void saveGroupe(Groupe groupe) {
        grouperepository.save(groupe);
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
    @Transactional
    public void inviteStudentsToGroupe(Groupe groupe, List<String> emails) {
        // Ensure we're working with a managed entity
        Groupe managedGroupe = grouperepository.findById(groupe.getId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        emails.forEach(email -> {
            userRepository.findByEmail(email).ifPresentOrElse(
                    user -> processExistingUser(managedGroupe, user),
                    () -> processNewInvitation(email, managedGroupe)
            );
        });

    @Override
    public void invitestudentsToGroupe(Groupe groupe, List<String> email) {

    }

    @Override
    public void removeStudentFromGroupe(Groupe groupe, String email) {

    }

}


    private void processExistingUser(Groupe groupe, User user) {
        // Check if user is already in the group
        if (user.getGroupe() != null && user.getGroupe().equals(groupe)) {
            return;
        }

        // Remove from previous group if any
        if (user.getGroupe() != null) {
            user.getGroupe().getUsers().remove(user);
        }

        // Add to new group
        user.setGroupe(groupe);
        groupe.getUsers().add(user); // Maintain bidirectional relationship
        userRepository.save(user);

        // Send invitation notification
        emailService.sendExistingUserInvitation(user.getEmail(), groupe);
    }

    private void processNewInvitation(String email, Groupe groupe) {
        // Create and send invitation to non-registered user
        String invitationToken = generateInvitationToken();
        saveInvitationRecord(email, groupe, invitationToken);
        emailService.sendNewUserInvitation(email, groupe, invitationToken);
    }

    private String generateInvitationToken() {
        // Implement token generation logic
        return UUID.randomUUID().toString();
    }

    private void saveInvitationRecord(String email, Groupe groupe, String token) {
        // Save to database (create Invitation entity if needed)
        invitationRepository.save(new Invitation(email, groupe, token));
    }
}