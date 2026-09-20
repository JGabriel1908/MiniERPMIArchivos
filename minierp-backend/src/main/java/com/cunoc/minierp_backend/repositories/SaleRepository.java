/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    
    List<Sale> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT v.cliente, SUM(v.total) as total FROM Venta v GROUP BY v.cliente ORDER BY total DESC")
    List<Object[]> findTopClientes(Pageable pageable);
}
