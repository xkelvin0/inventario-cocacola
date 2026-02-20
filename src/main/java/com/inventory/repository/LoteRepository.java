package com.inventory.repository;

import com.inventory.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer> {

    List<Lote> findByProductoIdProducto(Integer idProducto);

    Long countByProductoIdProducto(Integer idProducto);

    @org.springframework.data.jpa.repository.Query("SELECT COUNT(l) FROM Lote l WHERE l.fechaVencimiento <= :fecha")
    long countLotesCriticos(@org.springframework.data.repository.query.Param("fecha") java.time.LocalDate fecha);
}
