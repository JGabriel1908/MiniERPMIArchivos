package com.cunoc.minierp_backend.models.dto;

public class ExistenciaDTO {
    private Integer productoId;
    private String codigo;
    private String nombre;
    private String categoria;
    private Integer stockActual;
    private boolean stockBajo;

    public ExistenciaDTO(Integer productoId, String codigo, String nombre, String categoria,
                          Integer stockActual, boolean stockBajo) {
        this.productoId = productoId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stockActual = stockActual;
        this.stockBajo = stockBajo;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public boolean isStockBajo() {
        return stockBajo;
    }
}
