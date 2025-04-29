package ma.ensao.backend_pfa.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvitationDTO {
    private List<String> emails; // Liste des adresses email des étudiants à inviter
}
