package com.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Integer idMovimiento;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMovimiento tipo;

    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Size(max = 255)
    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha_registro", updatable = false)
    @jakarta.persistence.Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private java.util.Date fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = new java.util.Date();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventario", nullable = false)
    @JsonIgnore
    private Inventario inventario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote", nullable = false)
    @JsonIgnore
    private Lote lote;

    // Enum para tipo de movimiento
    public enum TipoMovimiento {
        INGRESO,
        SALIDA
    }

    // Constructores
    public Movimiento() {}

    public Movimiento(TipoMovimiento tipo, LocalDate fecha, Integer cantidad,
                      Inventario inventario, Lote lote) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.inventario = inventario;
        this.lote = lote;
    }

    // Getters y Setters
    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public java.util.Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(java.util.Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
