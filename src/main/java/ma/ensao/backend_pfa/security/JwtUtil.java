package ma.ensao.backend_pfa.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ma.ensao.backend_pfa.config.JwtProperties;
import ma.ensao.backend_pfa.entity.Role;
import ma.ensao.backend_pfa.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.Base64;
import java.security.NoSuchAlgorithmException;

@Component
public class JwtUtil {

    private String secretkey = "";
    @Autowired
    private RoleRepository roeRepository;

    public JwtUtil() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate the key", e);
        }
    }

    public String generateToken(String username,Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.getId());
        System.out.println(role);
        return Jwts.builder()
                .setClaims(claims)  
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 30 * 1000))  
                .signWith(getKey())  
                .compact();
    }
    public Role extractRole(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("role", Role.class);
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser() 
                .setSigningKey(getKey()) 
                .build()
                .parseClaimsJws(token)  
                .getBody();  
    }

    public boolean validateToken(String token) {
        try {
            final String userName = extractUserName(token);
            return (userName != null && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
