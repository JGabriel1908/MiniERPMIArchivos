/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.controllers;

import com.cunoc.minierp_backend.models.Product;
import com.cunoc.minierp_backend.services.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author gabrielh
 */
@RestController
@RequestMapping("/api/productos")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> listar() {
        return ResponseEntity.ok(service.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(service.crear(product));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(service.actualizar(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
