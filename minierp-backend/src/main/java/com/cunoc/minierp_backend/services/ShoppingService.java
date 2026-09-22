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
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingService {
    @Autowired private ShoppingRepository compraRepo;
    @Autowired private PurchaseDetailsRepository detalleCompraRepo;
    @Autowired private LotRepository loteRepo;
    @Autowired private MotionRepository movRepo;
    @Autowired private LogService logService;

    @Transactional
    public Shopping registrarCompra(Shopping compra, List<PurchaseDetails> detalles) {
        if (compra.getProveedor() == null || compra.getProveedor().getId() == null) {
            throw new RuntimeException("Debe seleccionar un proveedor");
        }
        if (compra.getUsuario() == null || compra.getUsuario().getId() == null) {
            throw new RuntimeException("La compra debe estar asociada a un usuario");
        }
        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("La compra debe incluir al menos un producto");
        }
        for (PurchaseDetails detalle : detalles) {
            if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
                throw new RuntimeException("Cada línea de la compra debe indicar un producto");
            }
            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a cero");
            }
            if (detalle.getCostoUnitario() == null || detalle.getCostoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("El costo unitario debe ser mayor a cero");
            }
        }

        BigDecimal totalCalculado = detalles.stream()
                .map(d -> d.getCostoUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        compra.setFecha(LocalDateTime.now());
        compra.setTotalCompra(totalCalculado);
        Shopping compraGuardada = compraRepo.save(compra);

        for (PurchaseDetails detalle : detalles) {
            detalle.setCompra(compraGuardada);
            detalle.setSubtotal(detalle.getCostoUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())));

            Lot nuevoLote = new Lot();
            nuevoLote.setProducto(detalle.getProducto());
            nuevoLote.setFechaIngreso(LocalDateTime.now());
            nuevoLote.setCantidadInicial(detalle.getCantidad());
            nuevoLote.setCantidadActual(detalle.getCantidad());
            nuevoLote.setCostoUnitario(detalle.getCostoUnitario());
            Lot loteGuardado = loteRepo.save(nuevoLote);

            detalle.setLoteGenerado(loteGuardado);
            detalleCompraRepo.save(detalle);

            Motion mov = new Motion();
            mov.setProducto(detalle.getProducto());
            mov.setUsuario(compra.getUsuario());
            mov.setTipoMovimiento("ENTRADA");
            mov.setCantidad(detalle.getCantidad());
            mov.setFecha(LocalDateTime.now());
            mov.setMotivo("Compra #" + compraGuardada.getId());
            movRepo.save(mov);
        }

        logService.registrar("CREACION", "COMPRAS", "Se registró la compra #" + compraGuardada.getId()
                + " a " + compraGuardada.getProveedor().getNombreProveedor() + " por Q" + totalCalculado);
        return compraGuardada;
    }

    public List<Shopping> listar() {
        return compraRepo.findAll();
    }

    public Shopping obtenerPorId(Integer id) {
        return compraRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
    }

    public List<PurchaseDetails> obtenerDetalle(Integer compraId) {
        return detalleCompraRepo.findByCompraId(compraId);
    }
}