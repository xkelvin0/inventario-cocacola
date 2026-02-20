package com.inventory.dto;

public class InventarioDTO {

    private Integer idInventario;
    private Integer stockActual;
    private Integer idProducto;
    private String nombreProducto;
    private String categoriaProducto;
    private Integer stockMinimo;
    private java.math.BigDecimal precioUnitario;

    // Constructores
    public InventarioDTO() {}

    public InventarioDTO(Integer idInventario, Integer stockActual, Integer idProducto,
                         String nombreProducto, String categoriaProducto, Integer stockMinimo,
                         java.math.BigDecimal precioUnitario) {
        this.idInventario = idInventario;
        this.stockActual = stockActual;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.categoriaProducto = categoriaProducto;
        this.stockMinimo = stockMinimo;
        this.precioUnitario = precioUnitario;
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

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public java.math.BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(java.math.BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    // Métodos auxiliares para UI
    public int getStockPercentage() {
        if (stockActual == null) return 0;
        return stockActual > 100 ? 100 : stockActual;
    }

    public String getStockColor() {
        if (stockActual == null) return "#cc0000";
        int minimo = (stockMinimo != null) ? stockMinimo : 10;
        
        if (stockActual <= minimo) return "#cc0000"; // Rojo Coca-Cola (Alerta)
        if (stockActual <= minimo * 2) return "#f59e0b"; // Naranja (Aviso)
        return "#10b981"; // Verde (OK)
    }
}
