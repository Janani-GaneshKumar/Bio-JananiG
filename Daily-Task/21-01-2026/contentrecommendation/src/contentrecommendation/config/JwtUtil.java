package com.janani.contentrecommendation.config;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;

@Component
//Spring creates and manages this class as a bean
public class JwtUtil {

    private final Key key;//secret ket used to sign & verify jwt
    private final long expiration;//how long the

    // Inject values from application.properties
    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration)//In this line the spring reads values form application.properties
    {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());//this line converts the value into secret key
        this.expiration = expiration;
    }

    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Collection<org.springframework.security.core.authority.SimpleGrantedAuthority> getAuthorities(String token) {
        Claims claims = extractClaims(token);
        String role = claims.get("role", String.class);
        return Collections.singletonList(
                new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role)
        );
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
