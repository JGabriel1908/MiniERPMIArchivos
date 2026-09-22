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
    @Autowired
    private LogService logService;

    public List<Customer> obtenerClientesActivos() {
        return clienteRepository.findByActivoTrue();
    }

    public Customer crearCliente(Customer nuevoCliente) {
        if (clienteRepository.existsByNit(nuevoCliente.getNit())) {
            throw new RuntimeException("Ya existe un cliente registrado con ese NIT");
        }
        nuevoCliente.setActivo(true); // Por defecto nace activo
        Customer guardado = clienteRepository.save(nuevoCliente);
        logService.registrar("CREACION", "CLIENTES", "Se creó el cliente '" + guardado.getNombre() + " " + guardado.getApellido() + "'");
        return guardado;
    }

    public Customer actualizarCliente(Integer id, Customer clienteActualizado) {
        Optional<Customer> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isPresent()) {
            Customer cliente = clienteExistente.get();
            if (!cliente.getNit().equals(clienteActualizado.getNit())
                    && clienteRepository.existsByNit(clienteActualizado.getNit())) {
                throw new RuntimeException("Ya existe un cliente registrado con ese NIT");
            }
            cliente.setNit(clienteActualizado.getNit());
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setDireccion(clienteActualizado.getDireccion());
            cliente.setCorreo(clienteActualizado.getCorreo());
            Customer actualizado = clienteRepository.save(cliente);
            logService.registrar("ACTUALIZACION", "CLIENTES", "Se actualizó el cliente '" + actualizado.getNombre() + " " + actualizado.getApellido() + "'");
            return actualizado;
        }
        return null; 
    }

    public void eliminarCliente(Integer id) {
        Optional<Customer> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            Customer cliente = clienteExistente.get();
            cliente.setActivo(false); 
            clienteRepository.save(cliente);
            logService.registrar("ELIMINACION", "CLIENTES", "Se desactivó el cliente '" + cliente.getNombre() + " " + cliente.getApellido() + "'");
        }
    }
}