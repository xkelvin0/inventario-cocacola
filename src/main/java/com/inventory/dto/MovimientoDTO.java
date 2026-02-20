package com.inventory.dto;

import com.inventory.entity.Movimiento.TipoMovimiento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MovimientoDTO {

    private Integer idMovimiento;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private TipoMovimiento tipo;

    private LocalDate fecha;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El ID del inventario es obligatorio")
    private Integer idInventario;

    @NotNull(message = "El ID del lote es obligatorio")
    private Integer idLote;

    private String motivo;

    // Campos adicionales para respuesta
    private String nombreProducto;
    private LocalDate fechaVencimientoLote;

    // Constructores
    public MovimientoDTO() {}

    public MovimientoDTO(Integer idMovimiento, TipoMovimiento tipo, LocalDate fecha,
                         Integer cantidad, Integer idInventario, Integer idLote, String motivo,
                         String nombreProducto, LocalDate fechaVencimientoLote) {
        this.idMovimiento = idMovimiento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.idInventario = idInventario;
        this.idLote = idLote;
        this.motivo = motivo;
        this.nombreProducto = nombreProducto;
        this.fechaVencimientoLote = fechaVencimientoLote;
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

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public LocalDate getFechaVencimientoLote() {
        return fechaVencimientoLote;
    }

    public void setFechaVencimientoLote(LocalDate fechaVencimientoLote) {
        this.fechaVencimientoLote = fechaVencimientoLote;
    }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
