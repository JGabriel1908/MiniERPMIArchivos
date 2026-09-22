/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.controllers;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Shopping;
import com.cunoc.minierp_backend.models.PurchaseDetails;
import com.cunoc.minierp_backend.repositories.UserRepository;
import com.cunoc.minierp_backend.services.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class ShoppingController {

    @Autowired
    private ShoppingService compraService;

    @Autowired
    private UserRepository userRepository;

    public static class CompraRequest {
        private Shopping compra;
        private List<PurchaseDetails> detalles;

        public Shopping getCompra() { return compra; }
        public void setCompra(Shopping compra) { this.compra = compra; }
        public List<PurchaseDetails> getDetalles() { return detalles; }
        public void setDetalles(List<PurchaseDetails> detalles) { this.detalles = detalles; }
    }

    @GetMapping
    public ResponseEntity<List<Shopping>> listar() {
        return ResponseEntity.ok(compraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(compraService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<List<PurchaseDetails>> obtenerDetalle(@PathVariable Integer id) {
        return ResponseEntity.ok(compraService.obtenerDetalle(id));
    }

    @PostMapping
    public ResponseEntity<?> registrarCompra(@RequestBody CompraRequest request) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            userRepository.findByUserName(userName).ifPresent(request.getCompra()::setUsuario);

            Shopping nuevaCompra = compraService.registrarCompra(request.getCompra(), request.getDetalles());
            return ResponseEntity.ok(nuevaCompra);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
