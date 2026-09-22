/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    @Query("SELECT l.producto, SUM(l.cantidadActual) as total FROM Lot l GROUP BY l.producto ORDER BY total ASC")
    List<Object[]> findProductosConMenorExistencia(Pageable pageable);
    List<Product> findByActivoTrue();

    boolean existsByCodigo(String codigo);
}