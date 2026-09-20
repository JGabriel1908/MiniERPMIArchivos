/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.controllers;

import com.cunoc.minierp_backend.models.User;
import com.cunoc.minierp_backend.models.dto.LoginRequest;
import com.cunoc.minierp_backend.repositories.UserRepository;
import com.cunoc.minierp_backend.security.JwtService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gabrielh
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class authentication {
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private PasswordEncoder passEncoder;
    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        Optional<User> userOptional = repoUser.findByUserName(request.getUserName());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(passEncoder.matches(request.getPassword(), user.getPassword())){
               String token;
                token = jwtService.generateToken(user.getUserName(), user.getRol());
                
                return ResponseEntity.ok().body( 
                    java.util.Map.of(
                        "token", token,
                        "usuario", user.getUserName(),
                        "rol", user.getRol()
                    )
                ); 
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
    
}
