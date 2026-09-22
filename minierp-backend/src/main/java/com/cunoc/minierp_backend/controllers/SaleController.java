/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.controllers;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Sale;
import com.cunoc.minierp_backend.models.SaleDetails;
import com.cunoc.minierp_backend.repositories.UserRepository;
import com.cunoc.minierp_backend.services.FacturaService;
import com.cunoc.minierp_backend.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class SaleController {

    @Autowired
    private SaleService ventaService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacturaService facturaService;

    public static class VentaRequest {
        private Sale venta;
        private List<SaleDetails> detalles;

        public Sale getVenta() { return venta; }
        public void setVenta(Sale venta) { this.venta = venta; }
        public List<SaleDetails> getDetalles() { return detalles; }
        public void setDetalles(List<SaleDetails> detalles) { this.detalles = detalles; }
    }

    @GetMapping
    public ResponseEntity<List<Sale>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ventaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<List<SaleDetails>> obtenerDetalle(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerDetalle(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Sale>> obtenerPorCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(ventaService.obtenerPorCliente(clienteId));
    }

    @GetMapping("/{id}/factura")
    public ResponseEntity<?> descargarFactura(@PathVariable Integer id) {
        try {
            Sale venta = ventaService.obtenerPorId(id);
            List<SaleDetails> detalles = ventaService.obtenerDetalle(id);
            byte[] pdf = facturaService.generarFactura(venta, detalles);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura-" + id + ".pdf")
                    .body(pdf);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequest request) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            userRepository.findByUserName(userName).ifPresent(request.getVenta()::setUsuario);

            Sale nuevaVenta = ventaService.registrarVenta(request.getVenta(), request.getDetalles());
            return ResponseEntity.ok(nuevaVenta);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
