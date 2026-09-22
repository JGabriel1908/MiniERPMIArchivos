package com.cunoc.minierp_backend.models.dto;

public class EntidadRankingDTO {
    private Integer id;
    private String nombre;
    private Number valor;

    public EntidadRankingDTO(Integer id, String nombre, Number valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Number getValor() {
        return valor;
    }
}
