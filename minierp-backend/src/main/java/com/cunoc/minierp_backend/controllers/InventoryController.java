package com.cunoc.minierp_backend.controllers;

import com.cunoc.minierp_backend.models.Motion;
import com.cunoc.minierp_backend.models.dto.ExistenciaDTO;
import com.cunoc.minierp_backend.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/existencias")
    public ResponseEntity<List<ExistenciaDTO>> existencias() {
        return ResponseEntity.ok(inventoryService.obtenerExistencias());
    }

    @GetMapping("/movimientos/{productoId}")
    public ResponseEntity<List<Motion>> movimientos(@PathVariable Integer productoId) {
        return ResponseEntity.ok(inventoryService.obtenerMovimientos(productoId));
    }
}
