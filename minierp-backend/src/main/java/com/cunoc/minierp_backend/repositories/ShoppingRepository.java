/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface ShoppingRepository extends JpaRepository<Shopping, Integer> {
    
    List<Shopping> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT c.proveedor, SUM(c.totalCompra) as total FROM Shopping c GROUP BY c.proveedor ORDER BY total DESC")
    List<Object[]> findTopProveedores(Pageable pageable);
}