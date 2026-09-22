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
    @Autowired
    private LogService logService;

    public List<Supplier> listarActivos() {
        return proveedorRepository.findByActivoTrue();
    }

    public Supplier obtenerPorId(Integer id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    public Supplier crear(Supplier proveedor) {
        if (proveedorRepository.existsByNit(proveedor.getNit())) {
            throw new RuntimeException("Ya existe un proveedor registrado con ese NIT");
        }
        proveedor.setActivo(true);
        Supplier guardado = proveedorRepository.save(proveedor);
        logService.registrar("CREACION", "PROVEEDORES", "Se creó el proveedor '" + guardado.getNombreProveedor() + "'");
        return guardado;
    }

    public Supplier actualizar(Integer id, Supplier datosActualizados) {
        Supplier proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        if (!proveedor.getNit().equals(datosActualizados.getNit())
                && proveedorRepository.existsByNit(datosActualizados.getNit())) {
            throw new RuntimeException("Ya existe un proveedor registrado con ese NIT");
        }

        proveedor.setNit(datosActualizados.getNit());
        proveedor.setNombreProveedor(datosActualizados.getNombreProveedor());
        proveedor.setTelefono(datosActualizados.getTelefono());
        proveedor.setDireccion(datosActualizados.getDireccion());

        Supplier actualizado = proveedorRepository.save(proveedor);
        logService.registrar("ACTUALIZACION", "PROVEEDORES", "Se actualizó el proveedor '" + actualizado.getNombreProveedor() + "'");
        return actualizado;
    }

    public void eliminar(Integer id) {
        proveedorRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            proveedorRepository.save(p);
            logService.registrar("ELIMINACION", "PROVEEDORES", "Se desactivó el proveedor '" + p.getNombreProveedor() + "'");
        });
    }
}
