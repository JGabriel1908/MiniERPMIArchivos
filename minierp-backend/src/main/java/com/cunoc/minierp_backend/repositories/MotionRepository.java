/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Motion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MotionRepository extends JpaRepository<Motion, Integer> {
    
    List<Motion> findByProductoIdOrderByFechaDesc(Integer productoId);

    @Query("SELECT m.producto, COUNT(m.id) as totalMovimientos FROM Motion m GROUP BY m.producto ORDER BY totalMovimientos DESC")
    List<Object[]> findProductosConMasMovimientos(Pageable pageable);
}
