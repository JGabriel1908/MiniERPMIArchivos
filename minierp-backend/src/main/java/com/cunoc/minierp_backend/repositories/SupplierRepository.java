/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByActivoTrue();

    boolean existsByNit(String nit);

}
