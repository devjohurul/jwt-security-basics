package com.johurulislam.main.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtUtil {
    @Value("${jwt.expiration}")
    private long JWR_EXP;
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(UserDetails userDetails) {
    List<String> grantedAuthorities=userDetails.getAuthorities().stream()
            .map(grantedAuthority -> grantedAuthority.getAuthority()).toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", grantedAuthorities);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(new Date().getTime()))
                .setExpiration(new Date(new Date().getTime()+ JWR_EXP))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

    }
    public boolean verifyJwtToken(String token) {
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            System.out.println("Invalid JWT token");
            return false;
        }
    }
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
    public List<String> extractAuthorities(String token) {
        return (List<String>) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("authorities", List.class);
    }
}
