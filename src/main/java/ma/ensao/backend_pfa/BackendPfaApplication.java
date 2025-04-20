package ma.ensao.backend_pfa;

import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import ma.ensao.backend_pfa.entity.Role;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.enums.RoleType;
import ma.ensao.backend_pfa.repository.RoleRepository;
import ma.ensao.backend_pfa.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendPfaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendPfaApplication.class, args);
    }

   /* @Bean
    CommandLineRunner start(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder,ProjectRepository projectRepository) {
        return args -> {
            // Créer des rôles
         /*   Role adminRole = new Role();
            adminRole.setName(RoleType.ADMIN);

            Role supervisorRole = new Role();
            supervisorRole.setName(RoleType.SUPERVISOR);
            Role studentRole = new Role();
            supervisorRole.setName(RoleType.STUDENT);

            Role userRole = new Role();
            userRole.setName(RoleType.USER);

            // Sauvegarder les rôles dans la base de données
            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            // Créer des utilisateurs
            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(adminRole);
            adminUser.setEnabled(true); // Utilisateur activé
            userService.saveUser(adminUser);

            User regularUser = new User();
            regularUser.setFirstName("John");
            regularUser.setLastName("Doe");
            regularUser.setEmail("john.doe@example.com");
            regularUser.setPassword("user123");
            regularUser.setRole(userRole);
            regularUser.setEnabled(true); // Utilisateur activé
            userService.saveUser(regularUser);


            //creer un nouveau projet
            User user_connected = userService.getConnectedUser();
            if (user_connected != null) {
                Project project = new Project();
                project.setTitre("premier projet");
                project.setDescription("notre premier projet");
                project.setCompetences("spring boot , jwt security , react, typescript");
                project.setStatut("vierge");
                project.setUser(user_connected);
                projectRepository.save(project);
            } else {
                System.out.println("Aucun utilisateur connecté trouvé au démarrage.");
            }

        };


    }

    */




}
