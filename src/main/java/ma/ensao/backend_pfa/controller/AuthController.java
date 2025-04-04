package ma.ensao.backend_pfa.controller;

import ma.ensao.backend_pfa.dto.AuthRequest;
import ma.ensao.backend_pfa.dto.AuthResponse;
import ma.ensao.backend_pfa.dto.RegisterRequest;
import ma.ensao.backend_pfa.dto.VerifyUserDto;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.security.JwtUtil;
import ma.ensao.backend_pfa.service.auth.AuthService;
import ma.ensao.backend_pfa.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    private UserService userService;
    
    private AuthService authService;
    
    private VerifyUserDto verifyUserDto;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        User newUser = authService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully: " + newUser.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        try {
            String token = authService.login(authRequest);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUserName(token);
        if (jwtUtil.validateToken(token)) {
            String newToken = jwtUtil.generateToken(username);
            return ResponseEntity.ok(new AuthResponse(newToken));
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
    
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
