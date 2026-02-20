package com.inventory.service;

import com.inventory.dto.MovimientoDTO;
import com.inventory.entity.Inventario;
import com.inventory.entity.Lote;
import com.inventory.entity.Movimiento;
import com.inventory.entity.Movimiento.TipoMovimiento;
import com.inventory.exception.BusinessException;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.InventarioRepository;
import com.inventory.repository.LoteRepository;
import com.inventory.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private LoteRepository loteRepository;

    /**
     * Obtener todos los movimientos
     */
    public List<MovimientoDTO> obtenerTodos() {
        return movimientoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener movimiento por ID
     */
    public MovimientoDTO obtenerPorId(Integer id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento", id));
        return convertirADTO(movimiento);
    }

    /**
     * Obtener movimientos por producto
     */
    public List<MovimientoDTO> obtenerPorProducto(Integer idProducto) {
        return movimientoRepository.findByProductoId(idProducto).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Registrar un nuevo movimiento (INGRESO o SALIDA).
     *
     * Reglas de negocio:
     * - Si el tipo es INGRESO: Aumenta el stock_actual en Inventario.
     * - Si el tipo es SALIDA: Disminuye el stock_actual.
     * - No permitir SALIDA si cantidad > stock_actual.
     * - No permitir cantidades negativas.
     */
    public MovimientoDTO registrar(MovimientoDTO dto) {
        // Validar cantidad positiva
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new BusinessException("La cantidad debe ser mayor a 0");
        }

        // Obtener inventario
        Inventario inventario = inventarioRepository.findById(dto.getIdInventario())
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", dto.getIdInventario()));

        // Obtener lote
        Lote lote = loteRepository.findById(dto.getIdLote())
                .orElseThrow(() -> new ResourceNotFoundException("Lote", dto.getIdLote()));

        // Validar que el lote pertenece al mismo producto que el inventario
        if (!lote.getProducto().getIdProducto().equals(inventario.getProducto().getIdProducto())) {
            throw new BusinessException(
                    "El lote no pertenece al mismo producto que el inventario. " +
                    "Lote (Producto ID: " + lote.getProducto().getIdProducto() + ") vs " +
                    "Inventario (Producto ID: " + inventario.getProducto().getIdProducto() + ")"
            );
        }

        // Aplicar reglas de negocio según tipo
        if (dto.getTipo() == TipoMovimiento.INGRESO) {
            // INGRESO: aumentar stock
            inventario.setStockActual(inventario.getStockActual() + dto.getCantidad());
        } else if (dto.getTipo() == TipoMovimiento.SALIDA) {
            // SALIDA: validar que hay suficiente stock
            if (dto.getCantidad() > inventario.getStockActual()) {
                throw new BusinessException(
                        "Stock insuficiente. Stock actual: " + inventario.getStockActual() +
                        ", Cantidad solicitada: " + dto.getCantidad()
                );
            }
            inventario.setStockActual(inventario.getStockActual() - dto.getCantidad());
        }

        // Actualizar el inventario
        inventarioRepository.save(inventario);

        // Crear y guardar el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setTipo(dto.getTipo());
        movimiento.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now());
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setInventario(inventario);
        movimiento.setLote(lote);
        movimiento.setMotivo(dto.getMotivo());

        movimiento = movimientoRepository.save(movimiento);
        return convertirADTO(movimiento);
    }

    // ========================
    // Conversiones DTO <-> Entity
    // ========================

    private MovimientoDTO convertirADTO(Movimiento movimiento) {
        return new MovimientoDTO(
                movimiento.getIdMovimiento(),
                movimiento.getTipo(),
                movimiento.getFecha(),
                movimiento.getCantidad(),
                movimiento.getInventario().getIdInventario(),
                movimiento.getLote().getIdLote(),
                movimiento.getMotivo(),
                movimiento.getInventario().getProducto().getNombre(),
                movimiento.getLote().getFechaVencimiento()
        );
    }
}
