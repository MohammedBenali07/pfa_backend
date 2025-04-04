package ma.ensao.backend_pfa.controller;

import ma.ensao.backend_pfa.dto.AuthRequest;
import ma.ensao.backend_pfa.dto.AuthResponse;
import ma.ensao.backend_pfa.dto.RegisterRequest;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.security.JwtUtil;
import ma.ensao.backend_pfa.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        User newUser = userService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully: " + newUser.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(authRequest.getEmail());

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        if (jwtUtil.validateToken(token, username)) {
            String newToken = jwtUtil.generateToken(username);
            return ResponseEntity.ok(new AuthResponse(newToken));
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
}
