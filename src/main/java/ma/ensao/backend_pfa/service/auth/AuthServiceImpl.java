package ma.ensao.backend_pfa.service.auth;

import lombok.RequiredArgsConstructor;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.security.JwtUtil;
import ma.ensao.backend_pfa.service.auth.AuthService;
import ma.ensao.backend_pfa.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return jwtUtil.generateToken(user);
    }

    @Override
    public String register(User user) {
        return userService.registerUser(user); // retourne le token de confirmation
    }

    @Override
    public void confirmAccount(String token) {
        userService.confirmUser(token);
    }
}
