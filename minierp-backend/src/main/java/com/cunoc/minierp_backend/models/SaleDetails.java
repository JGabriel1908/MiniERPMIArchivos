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
@Table(name = "detalle_ventas")
public class SaleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Sale venta;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Product producto;

    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = false)
    private Lot lote;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotalLinea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sale getVenta() {
        return venta;
    }

    public void setVenta(Sale venta) {
        this.venta = venta;
    }

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }

    public Lot getLote() {
        return lote;
    }

    public void setLote(Lot lote) {
        this.lote = lote;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotalLinea() {
        return subtotalLinea;
    }

    public void setSubtotalLinea(BigDecimal subtotalLinea) {
        this.subtotalLinea = subtotalLinea;
    }
    
    
}