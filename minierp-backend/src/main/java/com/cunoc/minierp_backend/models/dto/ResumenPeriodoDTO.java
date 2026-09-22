package com.cunoc.minierp_backend.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ResumenPeriodoDTO {
    private LocalDateTime periodo;
    private Long cantidadVentas;
    private BigDecimal total;

    public ResumenPeriodoDTO(LocalDateTime periodo, Long cantidadVentas, BigDecimal total) {
        this.periodo = periodo;
        this.cantidadVentas = cantidadVentas;
        this.total = total;
    }

    public LocalDateTime getPeriodo() {
        return periodo;
    }

    public Long getCantidadVentas() {
        return cantidadVentas;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
