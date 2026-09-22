/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.controllers;

/**
 *
 * @author gabrielh
 */

import com.cunoc.minierp_backend.models.Customer;
import com.cunoc.minierp_backend.services.CustomerService;
import com.cunoc.minierp_backend.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class CustomerController {

    @Autowired
    private CustomerService clienteService;

    @Autowired
    private SaleService ventaService;

    @GetMapping
    public ResponseEntity<List<Customer>> listarClientes() {
        return ResponseEntity.ok(clienteService.obtenerClientesActivos());
    }

    @GetMapping("/{id}/ventas")
    public ResponseEntity<?> historialDeCompras(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerPorCliente(id));
    }

    @PostMapping
    public ResponseEntity<?> guardarCliente(@RequestBody Customer cliente) {
        try {
            return ResponseEntity.ok(clienteService.crearCliente(cliente));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Integer id, @RequestBody Customer cliente) {
        try {
            Customer actualizado = clienteService.actualizarCliente(id, cliente);
            if (actualizado != null) {
                return ResponseEntity.ok(actualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
