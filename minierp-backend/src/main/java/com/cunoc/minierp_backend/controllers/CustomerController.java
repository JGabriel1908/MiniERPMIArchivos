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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class CustomerController {

    @Autowired
    private CustomerService clienteService;

    @GetMapping
    public ResponseEntity<List<Customer>> listarClientes() {
        return ResponseEntity.ok(clienteService.obtenerClientesActivos());
    }

    @PostMapping
    public ResponseEntity<Customer> guardarCliente(@RequestBody Customer cliente) {
        Customer nuevoCliente = clienteService.crearCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> actualizarCliente(@PathVariable Integer id, @RequestBody Customer cliente) {
        Customer actualizado = clienteService.actualizarCliente(id, cliente);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
