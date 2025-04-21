package ma.ensao.backend_pfa.service.groupe;

import ma.ensao.backend_pfa.entity.Groupe;

public interface EmailService {
    void sendExistingUserInvitation(String email, Groupe groupe);
    void sendNewUserInvitation(String email, Groupe groupe, String token);
}