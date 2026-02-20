package com.inventory.dto;

public class ReporteInventarioDTO {

    private Integer idProducto;
    private String nombreProducto;
    private String categoria;
    private Integer stockActual;
    private Long cantidadLotes;
    private Long totalIngresos;
    private Long totalSalidas;

    // Constructores
    public ReporteInventarioDTO() {}

    public ReporteInventarioDTO(Integer idProducto, String nombreProducto, String categoria,
                                 Integer stockActual, Long cantidadLotes,
                                 Long totalIngresos, Long totalSalidas) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.stockActual = stockActual;
        this.cantidadLotes = cantidadLotes;
        this.totalIngresos = totalIngresos;
        this.totalSalidas = totalSalidas;
    }

    // Getters y Setters
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Long getCantidadLotes() {
        return cantidadLotes;
    }

    public void setCantidadLotes(Long cantidadLotes) {
        this.cantidadLotes = cantidadLotes;
    }

    public Long getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(Long totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public Long getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(Long totalSalidas) {
        this.totalSalidas = totalSalidas;
    }
}
