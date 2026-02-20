package com.inventory.service;

import com.inventory.dto.InventarioDTO;
import com.inventory.dto.ReporteInventarioDTO;
import com.inventory.entity.Inventario;
import com.inventory.entity.Movimiento.TipoMovimiento;
import com.inventory.entity.Producto;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.InventarioRepository;
import com.inventory.repository.LoteRepository;
import com.inventory.repository.MovimientoRepository;
import com.inventory.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    /**
     * Obtener todos los inventarios
     */
    public List<InventarioDTO> obtenerTodos() {
        return inventarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener inventario por ID
     */
    public InventarioDTO obtenerPorId(Integer id) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", id));
        return convertirADTO(inventario);
    }

    /**
     * Obtener inventario por producto
     */
    public InventarioDTO obtenerPorProducto(Integer idProducto) {
        Inventario inventario = inventarioRepository.findByProductoIdProducto(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró inventario para el Producto con ID: " + idProducto));
        return convertirADTO(inventario);
    }

    /**
     * Generar reporte básico de inventario.
     * Incluye: producto, stock actual, cantidad de lotes, total ingresos, total salidas.
     */
    public List<ReporteInventarioDTO> generarReporte() {
        List<Producto> productos = productoRepository.findAll();
        List<ReporteInventarioDTO> reporte = new ArrayList<>();

        for (Producto producto : productos) {
            Integer idProducto = producto.getIdProducto();

            // Obtener inventario
            Inventario inventario = inventarioRepository.findByProductoIdProducto(idProducto)
                    .orElse(null);

            Integer stockActual = (inventario != null && inventario.getStockActual() != null) ? inventario.getStockActual() : 0;

            // Contar lotes
            Long cantidadLotes = loteRepository.countByProductoIdProducto(idProducto);

            // Sumar movimientos de ingreso y salida
            Long totalIngresos = movimientoRepository.sumCantidadByProductoAndTipo(
                    idProducto, TipoMovimiento.INGRESO);
            Long totalSalidas = movimientoRepository.sumCantidadByProductoAndTipo(
                    idProducto, TipoMovimiento.SALIDA);

            reporte.add(new ReporteInventarioDTO(
                    idProducto,
                    producto.getNombre(),
                    producto.getCategoria(),
                    stockActual,
                    cantidadLotes,
                    totalIngresos,
                    totalSalidas
            ));
        }

        return reporte;
    }

    /**
     * Obtener la valorización total del inventario (Stock * Precio)
     */
    public java.math.BigDecimal obtenerValorizacionTotal() {
        return inventarioRepository.findAll().stream()
                .filter(i -> i.getStockActual() != null && i.getProducto().getPrecioUnitario() != null)
                .map(i -> i.getProducto().getPrecioUnitario().multiply(new java.math.BigDecimal(i.getStockActual())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    /**
     * Obtener el conteo de lotes críticos (Vencidos o por vencer en 30 días)
     */
    public long obtenerConteoLotesCriticos() {
        return loteRepository.countLotesCriticos(java.time.LocalDate.now().plusDays(30));
    }

    /**
     * Obtener el Top 5 de productos con más movimientos
     */
    public List<java.util.Map<String, Object>> obtenerTopMovimientos() {
        return movimientoRepository.findTopProductosMasMovidos().stream()
                .limit(5)
                .map(obj -> {
                    java.util.Map<String, Object> map = new java.util.HashMap<>();
                    map.put("nombre", obj[0]);
                    map.put("cantidad", obj[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    // ========================
    // Conversiones DTO <-> Entity
    // ========================

    private InventarioDTO convertirADTO(Inventario inventario) {
        return new InventarioDTO(
                inventario.getIdInventario(),
                inventario.getStockActual() != null ? inventario.getStockActual() : 0,
                inventario.getProducto().getIdProducto(),
                inventario.getProducto().getNombre(),
                inventario.getProducto().getCategoria(),
                inventario.getProducto().getStockMinimo(),
                inventario.getProducto().getPrecioUnitario()
        );
    }
}
