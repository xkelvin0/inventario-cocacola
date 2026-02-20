package com.inventory.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class LoteDTO {

    private Integer idLote;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    @NotNull(message = "El ID del producto es obligatorio")
    private Integer idProducto;

    private String numeroLote;
    private Integer cantidadInicial;
    private String nombreProducto;

    // Constructores
    public LoteDTO() {}

    public LoteDTO(Integer idLote, LocalDate fechaVencimiento, Integer idProducto, 
                   String numeroLote, Integer cantidadInicial, String nombreProducto) {
        this.idLote = idLote;
        this.fechaVencimiento = fechaVencimiento;
        this.idProducto = idProducto;
        this.numeroLote = numeroLote;
        this.cantidadInicial = cantidadInicial;
        this.nombreProducto = nombreProducto;
    }

    // Getters y Setters
    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNumeroLote() { return numeroLote; }
    public void setNumeroLote(String numeroLote) { this.numeroLote = numeroLote; }

    public Integer getCantidadInicial() { return cantidadInicial; }
    public void setCantidadInicial(Integer cantidadInicial) { this.cantidadInicial = cantidadInicial; }
}
