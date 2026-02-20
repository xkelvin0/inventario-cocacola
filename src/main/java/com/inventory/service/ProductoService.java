package com.inventory.service;

import com.inventory.dto.ProductoDTO;
import com.inventory.entity.Inventario;
import com.inventory.entity.Producto;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.InventarioRepository;
import com.inventory.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    /**
     * Obtener todos los productos
     */
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener producto por ID
     */
    public ProductoDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        return convertirADTO(producto);
    }

    /**
     * Obtener productos por estado
     */
    public List<ProductoDTO> obtenerPorEstado(Boolean estado) {
        return productoRepository.findByEstado(estado).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Crear un nuevo producto.
     * Regla de negocio: Cada producto debe tener exactamente un Inventario.
     * Se crea automáticamente el Inventario con stock_actual = 0.
     */
    public ProductoDTO crear(ProductoDTO dto) {
        // Validación de nombre duplicado
        if (productoRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new RuntimeException("Ya existe un producto con el nombre: " + dto.getNombre());
        }

        // Validaciones de lógica de negocio (Nivel Ingeniero)
        if (dto.getPrecioUnitario() != null && dto.getPrecioUnitario().doubleValue() < 0) {
            throw new RuntimeException("El precio no puede ser negativo");
        }
        if (dto.getStockMinimo() != null && dto.getStockMinimo() < 0) {
            throw new RuntimeException("El stock mínimo no puede ser negativo");
        }

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setUnidadMedida(dto.getUnidadMedida());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        producto.setStockMinimo(dto.getStockMinimo() != null ? dto.getStockMinimo() : 0);
        producto.setEstado(dto.getEstado() != null ? dto.getEstado() : true);

        // Guardar producto primero
        producto = productoRepository.save(producto);

        // Crear automáticamente el inventario asociado (1:1)
        Inventario inventario = new Inventario(0, producto);
        inventarioRepository.save(inventario);

        return convertirADTO(producto);
    }

    /**
     * Actualizar un producto existente
     */
    public ProductoDTO actualizar(Integer id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));

        // Validación de nombre duplicado (si el nombre cambió)
        if (!producto.getNombre().equalsIgnoreCase(dto.getNombre()) && 
            productoRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new RuntimeException("Ya existe otro producto con el nombre: " + dto.getNombre());
        }

        // Validaciones de lógica de negocio
        if (dto.getPrecioUnitario() != null && dto.getPrecioUnitario().doubleValue() < 0) {
            throw new RuntimeException("El precio no puede ser negativo");
        }
        if (dto.getStockMinimo() != null && dto.getStockMinimo() < 0) {
            throw new RuntimeException("El stock mínimo no puede ser negativo");
        }

        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setUnidadMedida(dto.getUnidadMedida());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        producto.setStockMinimo(dto.getStockMinimo() != null ? dto.getStockMinimo() : 0);
        producto.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        
        producto = productoRepository.save(producto);

        return convertirADTO(producto);
    }

    /**
     * Eliminar un producto por ID
     */
    public void eliminar(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", id);
        }
        productoRepository.deleteById(id);
    }

    /**
     * Buscar productos por categoría
     */
    public List<ProductoDTO> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Buscar productos por nombre (parcial)
     */
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ========================
    // Conversiones DTO <-> Entity
    // ========================

    private ProductoDTO convertirADTO(Producto producto) {
        return new ProductoDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getDescripcion(),
                producto.getUnidadMedida(),
                producto.getPrecioUnitario(),
                producto.getStockMinimo(),
                producto.getEstado()
        );
    }
}
