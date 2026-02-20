package com.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Integer idInventario;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @Column(name = "fecha_actualizacion")
    @jakarta.persistence.Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private java.util.Date fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = new java.util.Date();
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false, unique = true)
    @JsonIgnore
    private Producto producto;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Movimiento> movimientos = new ArrayList<>();

    // Constructores
    public Inventario() {}

    public Inventario(Integer stockActual, Producto producto) {
        this.stockActual = stockActual;
        this.producto = producto;
    }

    // Getters y Setters
    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public java.util.Date getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(java.util.Date fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
