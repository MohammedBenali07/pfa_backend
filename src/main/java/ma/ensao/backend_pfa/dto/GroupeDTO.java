package ma.ensao.backend_pfa.dto;

import lombok.Data;

@Data
public class GroupeDTO {
    private Long id;
    private String nom;
    private String subject;
    private Long projectId; //ASSOCIER avec un projet lors de la création
    private Long representantId;
}
