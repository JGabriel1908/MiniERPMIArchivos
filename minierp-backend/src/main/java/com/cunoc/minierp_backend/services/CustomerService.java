/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.services;

/**
 *
 * @author gabrielh
 */

import com.cunoc.minierp_backend.models.Customer;
import com.cunoc.minierp_backend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository clienteRepository;

    // 1. LEER (Obtener todos los activos)
    public List<Customer> obtenerClientesActivos() {
        return clienteRepository.findByActivoTrue();
    }

    // 2. CREAR
    public Customer crearCliente(Customer nuevoCliente) {
        nuevoCliente.setActivo(true); // Por defecto nace activo
        return clienteRepository.save(nuevoCliente);
    }

    // 3. ACTUALIZAR
    public Customer actualizarCliente(Integer id, Customer clienteActualizado) {
        Optional<Customer> clienteExistente = clienteRepository.findById(id);
        
        if (clienteExistente.isPresent()) {
            Customer cliente = clienteExistente.get();
            cliente.setNit(clienteActualizado.getNit());
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setDireccion(clienteActualizado.getDireccion());
            cliente.setCorreo(clienteActualizado.getCorreo());
            return clienteRepository.save(cliente);
        }
        return null; // O podrías lanzar una excepción
    }

    // 4. ELIMINAR (Borrado Lógico)
    public void eliminarCliente(Integer id) {
        Optional<Customer> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            Customer cliente = clienteExistente.get();
            cliente.setActivo(false); // Lo "apagamos" en lugar de borrarlo de la BD
            clienteRepository.save(cliente);
        }
    }
}