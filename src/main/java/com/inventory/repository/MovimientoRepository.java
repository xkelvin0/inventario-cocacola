package com.inventory.repository;

import com.inventory.entity.Movimiento;
import com.inventory.entity.Movimiento.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    List<Movimiento> findByInventarioIdInventario(Integer idInventario);

    @Query("SELECT m FROM Movimiento m WHERE m.inventario.producto.idProducto = :idProducto ORDER BY m.fecha DESC")
    List<Movimiento> findByProductoId(@Param("idProducto") Integer idProducto);

    @Query("SELECT COALESCE(SUM(m.cantidad), 0) FROM Movimiento m WHERE m.inventario.producto.idProducto = :idProducto AND m.tipo = :tipo")
    Long sumCantidadByProductoAndTipo(@Param("idProducto") Integer idProducto, @Param("tipo") TipoMovimiento tipo);

    @Query("SELECT m.inventario.producto.nombre, COUNT(m) as total FROM Movimiento m GROUP BY m.inventario.producto.nombre ORDER BY total DESC")
    List<Object[]> findTopProductosMasMovidos();

    Long countByInventarioProductoIdProducto(Integer idProducto);

    boolean existsByLoteIdLote(Integer idLote);
}
