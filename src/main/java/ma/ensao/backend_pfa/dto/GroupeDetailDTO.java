package ma.ensao.backend_pfa.dto;

import lombok.Data;
import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.entity.User;

import java.util.List;

@Data
public class GroupeDetailDTO {
    private Long id;
    private String nom;
    private String subject;
    private Project project;
    private User representant;
    private List<User> users;
}
