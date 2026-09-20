/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.security;

import com.cunoc.minierp_backend.models.Rol;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
/**
 *
 * @author gabrielh
 */
@Service
public class JwtService {
    private final String SECRET_KEY = "MiniErpSecretKeyParaGenerarElTokenPorSeguridad";
    private final long SESSION_TIME = 86400000;
    
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    
    public String generateToken(String userName, Rol rol) {
        return Jwts.builder()
                .subject(userName) 
                .claim("rol", "ROLE_" + rol.name())
                .issuedAt(new Date(System.currentTimeMillis())) 
                .expiration(new Date(System.currentTimeMillis() + SESSION_TIME)) 
                .signWith(getSigningKey()) 
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser() 
                .verifyWith(getSigningKey()) 
                .build()
                .parseSignedClaims(token) 
                .getPayload(); 
    }

    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRol(String token) {
        return extractAllClaims(token).get("rol", String.class);
    }
    
    
}
