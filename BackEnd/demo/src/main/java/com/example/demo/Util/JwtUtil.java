package com.example.demo.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.security.sasl.AuthenticationException;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key sKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long Expiracao_Time = 1000 * 60 * 60 * 24 * 20;

    public static String generateToken(String email, Long userId){
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Expiracao_Time))
                .signWith(sKey)
                .compact();
    }

    public static Claims validarToken(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(sKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public static Long userIdPorToken(String token){
        Claims claim = validarToken(token);
        return claim.get("userId", Long.class);
    }

    public static String emailporToken(String token){
        Claims claim = validarToken(token);
        return claim.get("email", String.class);
    }

}
