package work.vietdefi.spring.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import work.vietdefi.spring.auth.dto.UserDTO;
import work.vietdefi.spring.auth.repository.UserRepository;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private UserRepository userRepository;

    @Value("${token.jwt.secret}") // Load secret from application.properties
    private String secretKey;

    @Value("${token.jwt.expiration}") // Load token expiration from config
    private long expirationTime;

    @Bean
    public Key signingKey() {
        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    // Generate JWT token
    public void generateToken(UserDTO userDTO) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userDTO.getUserId()));
        Instant now = Instant.now();
        Instant expired = now.plusSeconds(expirationTime);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expired))
                .signWith(signingKey(), SignatureAlgorithm.HS256) // Use the signingKey() method
                .compact();

        userDTO.setAccessToken(accessToken);
        userDTO.setAccessTokenExpired(expired.toEpochMilli());
    }

    public String extractSubject(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null; // Consider logging the exception for better debugging
        }
    }
}
