/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.SaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SaleDetailsRepository extends JpaRepository<SaleDetails, Integer> {
    
    // Reporte: Top 10 productos más vendidos
    @Query("SELECT dv.producto, SUM(dv.cantidad) as totalVendido FROM DetalleVenta dv GROUP BY dv.producto ORDER BY totalVendido DESC")
    List<Object[]> findTopProductosMasVendidos(Pageable pageable);

    // Reporte: Top 10 productos que han generado mayores ingresos
    @Query("SELECT dv.producto, SUM(dv.subtotalLinea) as ingresos FROM DetalleVenta dv GROUP BY dv.producto ORDER BY ingresos DESC")
    List<Object[]> findTopProductosMayoresIngresos(Pageable pageable);
}
