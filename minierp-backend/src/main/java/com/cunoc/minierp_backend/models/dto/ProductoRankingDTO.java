package com.cunoc.minierp_backend.models.dto;

public class ProductoRankingDTO {
    private Integer productoId;
    private String codigo;
    private String nombre;
    private Number valor;

    public ProductoRankingDTO(Integer productoId, String codigo, String nombre, Number valor) {
        this.productoId = productoId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor = valor;
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

    public Number getValor() {
        return valor;
    }
}
