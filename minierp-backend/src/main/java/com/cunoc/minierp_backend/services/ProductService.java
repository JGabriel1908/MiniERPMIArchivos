/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.services;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Product;
import com.cunoc.minierp_backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productoRepository;
    @Autowired
    private LogService logService;

    public List<Product> listarActivos() {
        return productoRepository.findByActivoTrue();
    }

    public Product obtenerPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Product crear(Product producto) {
        if (productoRepository.existsByCodigo(producto.getCodigo())) {
            throw new RuntimeException("Ya existe un producto registrado con ese código");
        }
        producto.setActivo(true);
        Product guardado = productoRepository.save(producto);
        logService.registrar("CREACION", "PRODUCTOS", "Se creó el producto '" + guardado.getNombre() + "'");
        return guardado;
    }

    public Product actualizar(Integer id, Product datosActualizados) {
        Product producto = obtenerPorId(id);

        if (!producto.getCodigo().equals(datosActualizados.getCodigo())
                && productoRepository.existsByCodigo(datosActualizados.getCodigo())) {
            throw new RuntimeException("Ya existe un producto registrado con ese código");
        }

        producto.setCodigo(datosActualizados.getCodigo());
        producto.setNombre(datosActualizados.getNombre());
        producto.setPrecioVenta(datosActualizados.getPrecioVenta());
        producto.setImagen(datosActualizados.getImagen());
        producto.setCategoria(datosActualizados.getCategoria());

        Product actualizado = productoRepository.save(producto);
        logService.registrar("ACTUALIZACION", "PRODUCTOS", "Se actualizó el producto '" + actualizado.getNombre() + "'");
        return actualizado;
    }

    public void eliminar(Integer id) {
        productoRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            productoRepository.save(p);
            logService.registrar("ELIMINACION", "PRODUCTOS", "Se desactivó el producto '" + p.getNombre() + "'");
        });
    }
}
