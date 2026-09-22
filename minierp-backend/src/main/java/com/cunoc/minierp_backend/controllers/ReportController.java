package com.cunoc.minierp_backend.controllers;

import com.cunoc.minierp_backend.models.Log;
import com.cunoc.minierp_backend.services.LogService;
import com.cunoc.minierp_backend.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private LogService logService;

    //productos y el intario

    @GetMapping("/productos/mas-vendidos")
    public ResponseEntity<?> productosMasVendidos(@RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(reportService.productosMasVendidos(limite));
    }

    @GetMapping("/productos/menor-existencia")
    public ResponseEntity<?> productosMenorExistencia(@RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(reportService.productosConMenorExistencia(limite));
    }

    @GetMapping("/productos/mas-movimientos")
    public ResponseEntity<?> productosMasMovimientos(@RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(reportService.productosConMasMovimientos(limite));
    }

    @GetMapping("/productos/{id}/movimientos")
    public ResponseEntity<?> historialMovimientos(@PathVariable Integer id) {
        return ResponseEntity.ok(reportService.historialMovimientos(id));
    }

    //las compras y los proveedores

    @GetMapping("/compras")
    public ResponseEntity<?> comprasPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(reportService.comprasPorRango(inicio, fin));
    }

    @GetMapping("/compras/top-proveedores")
    public ResponseEntity<?> topProveedores(@RequestParam(defaultValue = "5") int limite) {
        return ResponseEntity.ok(reportService.topProveedores(limite));
    }

    @GetMapping("/compras/productos-frecuentes")
    public ResponseEntity<?> productosFrecuentes(@RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(reportService.productosCompradosConFrecuencia(limite));
    }

    //las ventas y los clientes

    @GetMapping("/ventas")
    public ResponseEntity<?> ventasPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(reportService.ventasPorRango(inicio, fin));
    }

    @GetMapping("/ventas/top-clientes")
    public ResponseEntity<?> topClientes(@RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(reportService.topClientes(limite));
    }

    @GetMapping("/ventas/mayores-ingresos")
    public ResponseEntity<?> productosMayoresIngresos(@RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(reportService.productosConMayoresIngresos(limite));
    }

    @GetMapping("/ventas/resumen")
    public ResponseEntity<?> resumenVentas(@RequestParam(defaultValue = "month") String periodo) {
        return ResponseEntity.ok(reportService.resumenVentasPorPeriodo(periodo));
    }

    // ---- Logs ----

    @GetMapping("/logs")
    public ResponseEntity<List<Log>> logsRecientes(@RequestParam(defaultValue = "100") int limite) {
        return ResponseEntity.ok(logService.obtenerRecientes(limite));
    }
}
