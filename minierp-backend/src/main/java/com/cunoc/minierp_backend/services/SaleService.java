/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.services;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.*;
import com.cunoc.minierp_backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {
    private static final BigDecimal FACTOR_IVA = new BigDecimal("1.12");

    @Autowired private SaleRepository ventaRepo;
    @Autowired private SaleDetailsRepository detalleVentaRepo;
    @Autowired private LotRepository loteRepo;
    @Autowired private MotionRepository movRepo;
    @Autowired private LogService logService;

    @Transactional
    public Sale registrarVenta(Sale venta, List<SaleDetails> detalles) {
        if (venta.getCliente() == null || venta.getCliente().getId() == null) {
            throw new RuntimeException("Debe seleccionar un cliente");
        }
        if (venta.getUsuario() == null || venta.getUsuario().getId() == null) {
            throw new RuntimeException("La venta debe estar asociada a un usuario");
        }
        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("La venta debe incluir al menos un producto");
        }
        for (SaleDetails detalle : detalles) {
            if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
                throw new RuntimeException("Cada línea de la venta debe indicar un producto");
            }
            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a cero");
            }
            if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("El precio unitario debe ser mayor a cero");
            }
        }

        BigDecimal totalCalculado = detalles.stream()
                .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal subtotalCalculado = totalCalculado.divide(FACTOR_IVA, 2, RoundingMode.HALF_UP);
        BigDecimal ivaCalculado = totalCalculado.subtract(subtotalCalculado);

        venta.setFecha(LocalDateTime.now());
        venta.setSubtotal(subtotalCalculado);
        venta.setIva(ivaCalculado);
        venta.setTotal(totalCalculado);
        Sale ventaGuardada = ventaRepo.save(venta);

        for (SaleDetails detalle : detalles) {
            int cantidadFaltante = detalle.getCantidad();

            List<Lot> lotesDisponibles = loteRepo.findByProductoIdAndCantidadActualGreaterThanOrderByFechaIngresoAsc(detalle.getProducto().getId(), 0);

            for (Lot lote : lotesDisponibles) {
                if (cantidadFaltante == 0) break;

                int cantidadAExtraer = Math.min(lote.getCantidadActual(), cantidadFaltante);

                lote.setCantidadActual(lote.getCantidadActual() - cantidadAExtraer);
                loteRepo.save(lote);

                SaleDetails det = new SaleDetails();
                det.setVenta(ventaGuardada);
                det.setProducto(detalle.getProducto());
                det.setLote(lote);
                det.setCantidad(cantidadAExtraer);
                det.setPrecioUnitario(detalle.getPrecioUnitario());
                det.setSubtotalLinea(detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidadAExtraer)));
                detalleVentaRepo.save(det);

                cantidadFaltante -= cantidadAExtraer;
            }

            if (cantidadFaltante > 0) {
                throw new RuntimeException("No hay suficiente inventario para el producto: " + detalle.getProducto().getNombre());
            }

            Motion mov = new Motion();
            mov.setProducto(detalle.getProducto());
            mov.setUsuario(venta.getUsuario());
            mov.setTipoMovimiento("SALIDA");
            mov.setCantidad(detalle.getCantidad());
            mov.setFecha(LocalDateTime.now());
            mov.setMotivo("Venta #" + ventaGuardada.getId());
            movRepo.save(mov);
        }

        logService.registrar("CREACION", "VENTAS", "Se registró la venta #" + ventaGuardada.getId()
                + " a " + ventaGuardada.getCliente().getNombre() + " por Q" + totalCalculado);
        return ventaGuardada;
    }

    public List<Sale> listar() {
        return ventaRepo.findAll();
    }

    public Sale obtenerPorId(Integer id) {
        return ventaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public List<SaleDetails> obtenerDetalle(Integer ventaId) {
        return detalleVentaRepo.findByVentaId(ventaId);
    }

    public List<Sale> obtenerPorCliente(Integer clienteId) {
        return ventaRepo.findByClienteIdOrderByFechaDesc(clienteId);
    }
}
