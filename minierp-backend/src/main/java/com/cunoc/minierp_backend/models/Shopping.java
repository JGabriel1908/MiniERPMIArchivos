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
import java.time.LocalDateTime;

@Entity
@Table(name = "compras")
public class Shopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Supplier proveedor;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @Column(name = "fecha_compra")
    private LocalDateTime fecha;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCompra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Supplier getProveedor() {
        return proveedor;
    }

    public void setProveedor(Supplier proveedor) {
        this.proveedor = proveedor;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }
    
    
}
