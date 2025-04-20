package ma.ensao.backend_pfa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensao.backend_pfa.entity.Role;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}
