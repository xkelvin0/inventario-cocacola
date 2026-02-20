package com.inventory.repository;

import com.inventory.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByEstado(Boolean estado);

    boolean existsByNombreIgnoreCase(String nombre);
}
