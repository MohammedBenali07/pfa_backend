package ma.ensao.backend_pfa.controller;


import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.repository.ProjectRepository;
import ma.ensao.backend_pfa.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@RestController
@RequestMapping("/api/Encadrant/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;
    @PostMapping
    public void useronline(){
        User user=userService.getConnectedUser();

    }
    @PostMapping("/create-projects")
    public ResponseEntity<Project> creerProjet(@RequestBody Project projet) {
        User user = userService.getConnectedUser();
        projet.setUser(user);
        Project saved = projectRepository.save(projet);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/my-projets")
    public ResponseEntity<List<Project>> getProjets() {
        User user = userService.getConnectedUser();
        List<Project> projets = projectRepository.findByUser(user);
       // System.out.println("Projets de l'utilisateur connecté :");
        //projets.forEach(System.out::println);

        return ResponseEntity.ok(projets);
    }
}
