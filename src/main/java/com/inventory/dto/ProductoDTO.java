package com.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductoDTO {

    private Integer idProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String categoria;

    private String descripcion;
    private String unidadMedida;
    private java.math.BigDecimal precioUnitario;
    private Integer stockMinimo;
    private Boolean estado;

    // Constructores
    public ProductoDTO() {}

    public ProductoDTO(Integer idProducto, String nombre, String categoria, String descripcion, 
                       String unidadMedida, java.math.BigDecimal precioUnitario, 
                       Integer stockMinimo, Boolean estado) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public java.math.BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(java.math.BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
