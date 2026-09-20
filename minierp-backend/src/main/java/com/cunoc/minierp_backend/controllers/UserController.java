package com.cunoc.minierp_backend.controllers;

import com.cunoc.minierp_backend.models.User;
import com.cunoc.minierp_backend.models.Rol;
import com.cunoc.minierp_backend.repositories.UserRepository;
// Importa tu repositorio de usuarios aquí
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository usuarioRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody User nuevoUsuario) {
        
        String passwordEncriptada = passwordEncoder.encode(nuevoUsuario.getPassword());
        nuevoUsuario.setPassword(passwordEncriptada);
        
        User usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario creado con éxito");
        respuesta.put("usuario", usuarioGuardado);
        
        return ResponseEntity.ok(respuesta);
    }
    
    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<User> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }
}
