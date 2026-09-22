package com.cunoc.minierp_backend.services;

import com.cunoc.minierp_backend.models.User;
import com.cunoc.minierp_backend.models.dto.UserResponse;
import com.cunoc.minierp_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LogService logService;

    public List<UserResponse> listarActivos() {
        return usuarioRepository.findByActivoTrue().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse obtenerPorId(Integer id) {
        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UserResponse(usuario);
    }

    public UserResponse crear(User nuevoUsuario) {
        if (usuarioRepository.existsByUserName(nuevoUsuario.getUserName())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        nuevoUsuario.setActivo(true);
        User usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        logService.registrar("CREACION", "USUARIOS",
                "Se creó el usuario '" + usuarioGuardado.getUserName() + "' con rol " + usuarioGuardado.getRol());

        return new UserResponse(usuarioGuardado);
    }

    public UserResponse actualizar(Integer id, User datosActualizados) {
        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getUserName().equals(datosActualizados.getUserName())
                && usuarioRepository.existsByUserName(datosActualizados.getUserName())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        usuario.setUserName(datosActualizados.getUserName());
        usuario.setName(datosActualizados.getName());
        usuario.setLastName(datosActualizados.getLastName());
        usuario.setRol(datosActualizados.getRol());

        if (datosActualizados.getPassword() != null && !datosActualizados.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(datosActualizados.getPassword()));
        }

        User usuarioActualizado = usuarioRepository.save(usuario);

        logService.registrar("ACTUALIZACION", "USUARIOS",
                "Se actualizó el usuario '" + usuarioActualizado.getUserName() + "'");

        return new UserResponse(usuarioActualizado);
    }

    public void eliminar(Integer id) {
        Optional<User> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            User usuario = usuarioExistente.get();
            usuario.setActivo(false);
            usuarioRepository.save(usuario);

            logService.registrar("ELIMINACION", "USUARIOS",
                    "Se desactivó el usuario '" + usuario.getUserName() + "'");
        }
    }
}
