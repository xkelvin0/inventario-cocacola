package com.inventory.service;

import com.inventory.dto.LoteDTO;
import com.inventory.entity.Lote;
import com.inventory.entity.Producto;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.exception.BusinessException;
import com.inventory.repository.LoteRepository;
import com.inventory.repository.MovimientoRepository;
import com.inventory.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    /**
     * Obtener todos los lotes
     */
    public List<LoteDTO> obtenerTodos() {
        return loteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener lote por ID
     */
    public LoteDTO obtenerPorId(Integer id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", id));
        return convertirADTO(lote);
    }

    /**
     * Obtener lotes por producto
     */
    public List<LoteDTO> obtenerPorProducto(Integer idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new ResourceNotFoundException("Producto", idProducto);
        }
        return loteRepository.findByProductoIdProducto(idProducto).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Crear un nuevo lote para un producto
     */
    public LoteDTO crear(LoteDTO dto) {
        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto", dto.getIdProducto()));

        Lote lote = new Lote();
        lote.setFechaVencimiento(dto.getFechaVencimiento());
        lote.setNumeroLote(dto.getNumeroLote());
        lote.setCantidadInicial(dto.getCantidadInicial() != null ? dto.getCantidadInicial() : 0);
        lote.setProducto(producto);

        lote = loteRepository.save(lote);
        return convertirADTO(lote);
    }

    /**
     * Eliminar un lote por ID
     */
    public void eliminar(Integer id) {
        if (!loteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lote", id);
        }

        // V-06.1: No permitir eliminar si el lote tiene movimientos (historial)
        // Usamos countByLoteIdLote que debería existir o lo agregamos al repo de movimientos
        // Pero ya agregamos countByInventarioProductoIdProducto antes. 
        // Vamos a verificar si MovimientoRepository tiene lo que necesitamos para Lote.
        if (movimientoRepository.existsByLoteIdLote(id)) {
            throw new BusinessException("No se puede eliminar el lote porque tiene historial de movimientos registrado.");
        }

        loteRepository.deleteById(id);
    }

    // ========================
    // Conversiones DTO <-> Entity
    // ========================

    private LoteDTO convertirADTO(Lote lote) {
        return new LoteDTO(
                lote.getIdLote(),
                lote.getFechaVencimiento(),
                lote.getProducto().getIdProducto(),
                lote.getNumeroLote(),
                lote.getCantidadInicial(),
                lote.getProducto().getNombre()
        );
    }
}
