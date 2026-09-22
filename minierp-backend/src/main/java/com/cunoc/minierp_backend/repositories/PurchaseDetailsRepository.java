/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PurchaseDetailsRepository extends JpaRepository<PurchaseDetails, Integer> {

    @Query("SELECT dc.producto, COUNT(dc.compra) as frecuencia FROM PurchaseDetails dc GROUP BY dc.producto ORDER BY frecuencia DESC")
    List<Object[]> findProductosCompradosConFrecuencia(Pageable pageable);

    List<PurchaseDetails> findByCompraId(Integer compraId);
}
