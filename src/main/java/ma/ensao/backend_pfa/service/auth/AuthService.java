package ma.ensao.backend_pfa.service.auth;
import org.springframework.stereotype.Service;

import ma.ensao.backend_pfa.dto.AuthRequest;
import ma.ensao.backend_pfa.dto.RegisterRequest;
import ma.ensao.backend_pfa.dto.VerifyUserDto;
import ma.ensao.backend_pfa.entity.User;
@Service
public interface AuthService {
    String login(AuthRequest authRequest);
	User registerUser(RegisterRequest registerRequest);
	void verifyUser(VerifyUserDto verifyUserDto);
	void sendVerificationEmail(User user,String verificationCode);
	void resendVerificationCode(String email);
}
