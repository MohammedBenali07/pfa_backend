package ma.ensao.backend_pfa.service.groupe;

import ma.ensao.backend_pfa.entity.Groupe;
import ma.ensao.backend_pfa.entity.Project;

import java.util.List;

public interface GroupeService {
    public List<Groupe> listAllGroupes();

    public void saveGroupe(Groupe groupe);

    public Groupe getGroupeById(long id);

    public void deletegroupe(long id);

    public List<Groupe> listAllGroupesByProject(Project project);

    public void addProjectToGroupe(Groupe groupe, Project project);

    public List<Project> listAllProjectsByGroupe(Groupe groupe);

    @Transactional
    public void inviteStudentsToGroupe(Groupe groupe, List<String> emails) {
        // Ensure we're working with a managed entity
        Groupe managedGroupe = groupeRepository.findById(groupe.getId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        emails.forEach(email -> {
            userRepository.findByEmail(email).ifPresentOrElse(
                    user -> processExistingUser(managedGroupe, user),
                    () -> processNewInvitation(email, managedGroupe)
            );
        });

    public void removeStudentFromGroupe(Groupe groupe, String email);
}