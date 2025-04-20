package ma.ensao.backend_pfa.service.project;


import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceIml implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> listAllProjects(){
        return projectRepository.findAll();
    };

    @Override
    public void saveProject(Project project){
        projectRepository.save(project);
    }

    @Override
    public Project getProjectById(long id){
        return projectRepository.findById(id).get();
    }

    @Override
    public void deleteproject(long id){
        projectRepository.deleteById(id);
   }
/*
@Service
    public class StudentService {

        @Autowired
        private StudentRepository repo;

        public List<Student> listAll() {
            return repo.findAll();
        }

        public void save(Student std) {
            repo.save(std);
        }

        public Student get(long id) {
            return repo.findById(id).get();
        }

        public void delete(long id) {
            repo.deleteById(id);
        }

    }

 */
}
