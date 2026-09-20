/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.services;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Supplier;
import com.cunoc.minierp_backend.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository proveedorRepository;

    public List<Supplier> listarActivos() {
        return proveedorRepository.findByActivoTrue(); 
    }

    public Supplier guardar(Supplier proveedor) {
        proveedor.setActivo(true);
        return proveedorRepository.save(proveedor);
    }

    public void eliminar(Integer id) {
        proveedorRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            proveedorRepository.save(p);
        });
    }
}
