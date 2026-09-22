package com.cunoc.minierp_backend.services;

import com.cunoc.minierp_backend.models.*;
import com.cunoc.minierp_backend.models.dto.EntidadRankingDTO;
import com.cunoc.minierp_backend.models.dto.ProductoRankingDTO;
import com.cunoc.minierp_backend.models.dto.ResumenPeriodoDTO;
import com.cunoc.minierp_backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired private ProductRepository productoRepo;
    @Autowired private SaleDetailsRepository detalleVentaRepo;
    @Autowired private PurchaseDetailsRepository detalleCompraRepo;
    @Autowired private MotionRepository movimientoRepo;
    @Autowired private ShoppingRepository compraRepo;
    @Autowired private SaleRepository ventaRepo;

    private Pageable top(int limite) {
        return PageRequest.of(0, limite);
    }

    private ProductoRankingDTO aProductoRanking(Object[] fila) {
        Product producto = (Product) fila[0];
        Number valor = (Number) fila[1];
        return new ProductoRankingDTO(producto.getId(), producto.getCodigo(), producto.getNombre(), valor);
    }


    public List<ProductoRankingDTO> productosMasVendidos(int limite) {
        return detalleVentaRepo.findTopProductosMasVendidos(top(limite)).stream()
                .map(this::aProductoRanking)
                .collect(Collectors.toList());
    }

    public List<ProductoRankingDTO> productosConMenorExistencia(int limite) {
        return productoRepo.findProductosConMenorExistencia(top(limite)).stream()
                .map(this::aProductoRanking)
                .collect(Collectors.toList());
    }

    public List<ProductoRankingDTO> productosConMasMovimientos(int limite) {
        return movimientoRepo.findProductosConMasMovimientos(top(limite)).stream()
                .map(this::aProductoRanking)
                .collect(Collectors.toList());
    }

    public List<Motion> historialMovimientos(Integer productoId) {
        return movimientoRepo.findByProductoIdOrderByFechaDesc(productoId);
    }


    public List<Shopping> comprasPorRango(LocalDateTime inicio, LocalDateTime fin) {
        return compraRepo.findByFechaBetween(inicio, fin);
    }

    public List<EntidadRankingDTO> topProveedores(int limite) {
        return compraRepo.findTopProveedores(top(limite)).stream()
                .map(fila -> {
                    Supplier proveedor = (Supplier) fila[0];
                    BigDecimal total = (BigDecimal) fila[1];
                    return new EntidadRankingDTO(proveedor.getId(), proveedor.getNombreProveedor(), total);
                })
                .collect(Collectors.toList());
    }

    public List<ProductoRankingDTO> productosCompradosConFrecuencia(int limite) {
        return detalleCompraRepo.findProductosCompradosConFrecuencia(top(limite)).stream()
                .map(this::aProductoRanking)
                .collect(Collectors.toList());
    }


    public List<Sale> ventasPorRango(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepo.findByFechaBetween(inicio, fin);
    }

    public List<EntidadRankingDTO> topClientes(int limite) {
        return ventaRepo.findTopClientes(top(limite)).stream()
                .map(fila -> {
                    Customer cliente = (Customer) fila[0];
                    BigDecimal total = (BigDecimal) fila[1];
                    String nombreCompleto = cliente.getNombre() + " " + cliente.getApellido();
                    return new EntidadRankingDTO(cliente.getId(), nombreCompleto, total);
                })
                .collect(Collectors.toList());
    }

    public List<ProductoRankingDTO> productosConMayoresIngresos(int limite) {
        return detalleVentaRepo.findTopProductosMayoresIngresos(top(limite)).stream()
                .map(this::aProductoRanking)
                .collect(Collectors.toList());
    }

    public List<ResumenPeriodoDTO> resumenVentasPorPeriodo(String unidad) {
        List<String> unidadesValidas = List.of("day", "week", "month");
        String unidadSegura = unidadesValidas.contains(unidad) ? unidad : "month";

        return ventaRepo.resumenPorPeriodo(unidadSegura).stream()
                .map(fila -> new ResumenPeriodoDTO(
                        (LocalDateTime) fila[0],
                        ((Number) fila[1]).longValue(),
                        (BigDecimal) fila[2]
                ))
                .collect(Collectors.toList());
    }
}
