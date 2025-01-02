package com.diegonobretecnologia.ferramenteiro.config;




import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import io.jsonwebtoken.JwtParser;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String SECRET = "bTUybDlpWFg0aEs5cTBmNno3dTF3OG8wYTNiMmM0ZDViNmY3ZzhoOWkwanFrMmwxM200bjVvNnE3cThyOXMwdDF1MnYzdzR4NXk2eg==";
    private final Key SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode(SECRET), SignatureAlgorithm.HS512.getJcaName());
    private static final long EXPIRATION_TIME = 86400000; // 24 horas

    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_" + role);
        logger.info("JwtUtil: Gerando token para email: {}, com papel: {}", email, claims.get("role"));
        return Jwts.builder()
                .setClaims(claims) // Adiciona os claims (incluindo o papel)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();

    }

    public String getEmailFromToken(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY).build();
        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    public String getRoleFromToken(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY).build();
        String role = parser.parseClaimsJws(token).getBody().get("role", String.class);
        return role;
    }

    public boolean isTokenValid(String token) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY).build();
            parser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}