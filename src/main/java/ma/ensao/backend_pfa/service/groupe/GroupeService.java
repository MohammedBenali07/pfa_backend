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
    public void invitestudentsToGroupe(Groupe groupe, List<String> email);
    public void removeStudentFromGroupe(Groupe groupe, String email);
}