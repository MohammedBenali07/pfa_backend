package ma.ensao.backend_pfa.controller;


import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private  UserService userService;

    @GetMapping("/tokentest")
    public ResponseEntity<User> tokenTest() {
        User user = userService.getConnectedUser();
        System.out.println("user coonected "+user.getLastName()+" ' "+user.getFirstName());
        return ResponseEntity.ok(user);
    }
    @GetMapping("/debug")
    public ResponseEntity<String> debugEndpoint() {
        System.out.println("===== DEBUG: Requête reçue du frontend =====");
        User user=userService.getConnectedUser();
        System.out.println("user coonected "+user.getLastName()+" ' "+user.getFirstName());
        return ResponseEntity.ok("Message reçu par le backend");
    }

}
