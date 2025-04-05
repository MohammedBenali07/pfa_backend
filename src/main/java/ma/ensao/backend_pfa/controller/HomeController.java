package ma.ensao.backend_pfa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String accueil() {
        return "Bienvenue sur la page d'accueil !";
    }
}