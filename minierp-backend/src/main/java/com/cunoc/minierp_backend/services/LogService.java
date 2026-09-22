package com.cunoc.minierp_backend.services;

import com.cunoc.minierp_backend.models.Log;
import com.cunoc.minierp_backend.repositories.LogRepository;
import com.cunoc.minierp_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    public void registrar(String accion, String modulo, String descripcion) {
        String userName = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;

        userRepository.findByUserName(userName).ifPresent(usuario -> {
            Log log = new Log();
            log.setUsuario(usuario);
            log.setAccion(accion);
            log.setModulo(modulo);
            log.setFecha(LocalDateTime.now());
            log.setDescripcionDetallada(descripcion);
            logRepository.save(log);
        });
    }

    public List<Log> obtenerRecientes(int limite) {
        Pageable pageable = PageRequest.of(0, limite);
        return logRepository.findAllByOrderByFechaDesc(pageable);
    }

    public List<Log> obtenerPorModulo(String modulo, int limite) {
        Pageable pageable = PageRequest.of(0, limite);
        return logRepository.findByModuloOrderByFechaDesc(modulo, pageable);
    }
}
