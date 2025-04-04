package ma.ensao.backend_pfa.controller;

import ma.ensao.backend_pfa.util.EmailUtil;
import ma.ensao.backend_pfa.dto.ResponseDTO;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*@RestController
@RequestMapping("/api/account")*/
public class AccountConfirmationController {

   /* @Autowired
    private UserService userService;

    @Autowired
    private EmailUtil emailUtil;

    @PostMapping("/send-confirmation")
    public ResponseEntity<ResponseDTO> sendConfirmationEmail(@RequestParam("email") String email) {
        boolean emailSent = emailUtil.sendVerificationEmail(email, email, email);

        if (emailSent) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("Un e-mail de confirmation a été envoyé avec succès à " + email));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO("Échec de l'envoi de l'e-mail de confirmation"));
        }
    }

    @GetMapping("/confirm/{confirmationCode}")
    public ResponseEntity<ResponseDTO> confirmAccount(@PathVariable("confirmationCode") String confirmationCode) {
        boolean isConfirmed = userService.confirmAccount(confirmationCode);

        if (isConfirmed) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("Le compte a été confirmé avec succès"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO("Échec de la confirmation du compte. Code invalide ou expiré."));
        }
    }

    @PostMapping("/resend-confirmation")
    public ResponseEntity<ResponseDTO> resendConfirmationCode(@RequestParam("email") String email) {
        boolean emailSent = emailUtil.resendConfirmationCode(email);

        if (emailSent) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("Le code de confirmation a été renvoyé à " + email));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO("Échec de l'envoi du code de confirmation"));
        }
    }*/
}
