package com.cunoc.minierp_backend.controllers;

import com.cunoc.minierp_backend.models.User;
import com.cunoc.minierp_backend.models.dto.UserResponse;
import com.cunoc.minierp_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService usuarioService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(usuarioService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody User nuevoUsuario) {
        try {
            return ResponseEntity.ok(usuarioService.crear(nuevoUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody User datosActualizados) {
        try {
            return ResponseEntity.ok(usuarioService.actualizar(id, datosActualizados));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
