package ma.ensao.backend_pfa.service.project;

import ma.ensao.backend_pfa.entity.Project;

import java.util.List;

public interface ProjectService {


    public List<Project> listAllProjects();

    public void saveProject(Project project);

    public Project getProjectById(long id);

    public void deleteproject(long id);

}
