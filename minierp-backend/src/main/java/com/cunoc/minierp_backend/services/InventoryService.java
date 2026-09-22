package com.cunoc.minierp_backend.services;

import com.cunoc.minierp_backend.models.Lot;
import com.cunoc.minierp_backend.models.Motion;
import com.cunoc.minierp_backend.models.Product;
import com.cunoc.minierp_backend.models.dto.ExistenciaDTO;
import com.cunoc.minierp_backend.repositories.LotRepository;
import com.cunoc.minierp_backend.repositories.MotionRepository;
import com.cunoc.minierp_backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    public static final int UMBRAL_STOCK_BAJO = 10;

    @Autowired
    private ProductRepository productoRepository;

    @Autowired
    private LotRepository loteRepository;

    @Autowired
    private MotionRepository movimientoRepository;

    public List<ExistenciaDTO> obtenerExistencias() {
        List<Product> productos = productoRepository.findByActivoTrue();
        List<Lot> lotes = loteRepository.findAll();

        Map<Integer, Integer> stockPorProducto = lotes.stream()
                .collect(Collectors.groupingBy(
                        l -> l.getProducto().getId(),
                        Collectors.summingInt(Lot::getCantidadActual)
                ));

        return productos.stream()
                .map(p -> {
                    int stock = stockPorProducto.getOrDefault(p.getId(), 0);
                    return new ExistenciaDTO(
                            p.getId(),
                            p.getCodigo(),
                            p.getNombre(),
                            p.getCategoria() != null ? p.getCategoria().getNombre() : null,
                            stock,
                            stock < UMBRAL_STOCK_BAJO
                    );
                })
                .collect(Collectors.toList());
    }

    public List<Motion> obtenerMovimientos(Integer productoId) {
        return movimientoRepository.findByProductoIdOrderByFechaDesc(productoId);
    }
}
