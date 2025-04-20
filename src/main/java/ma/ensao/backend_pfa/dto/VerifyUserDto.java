package ma.ensao.backend_pfa.dto;

import lombok.Data;
import ma.ensao.backend_pfa.entity.Role;

@Data
public class VerifyUserDto {
    private String email;
    private String verificationCode;
}