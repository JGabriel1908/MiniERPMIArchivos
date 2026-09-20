/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Integer> {
    List<Lot> findByProductoIdAndCantidadActualGreaterThanOrderByFechaIngresoAsc(Integer productoId, Integer cantidad);
}
