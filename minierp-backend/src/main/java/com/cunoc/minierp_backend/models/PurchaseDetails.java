/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.models;

/**
 *
 * @author gabrielh
 */

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_compras")
public class PurchaseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Shopping compra;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Product producto;

    @ManyToOne
    @JoinColumn(name = "lote_generado_id")
    private Lot loteGenerado;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "costo_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoUnitario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Shopping getCompra() {
        return compra;
    }

    public void setCompra(Shopping compra) {
        this.compra = compra;
    }

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }

    public Lot getLoteGenerado() {
        return loteGenerado;
    }

    public void setLoteGenerado(Lot loteGenerado) {
        this.loteGenerado = loteGenerado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }
    
    
}