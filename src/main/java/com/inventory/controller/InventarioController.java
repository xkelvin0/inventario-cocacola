package com.inventory.controller;

import com.inventory.dto.InventarioDTO;
import com.inventory.dto.ReporteInventarioDTO;
import com.inventory.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@Tag(name = "Inventarios", description = "Consulta de Inventario y Reportes")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Listar todo el inventario")
    public ResponseEntity<List<InventarioDTO>> listarTodos() {
        return ResponseEntity.ok(inventarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener inventario por su ID")
    public ResponseEntity<InventarioDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.obtenerPorId(id));
    }

    @GetMapping("/producto/{idProducto}")
    @Operation(summary = "Obtener inventario por producto")
    public ResponseEntity<InventarioDTO> obtenerPorProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(inventarioService.obtenerPorProducto(idProducto));
    }

    @GetMapping("/reporte")
    @Operation(summary = "Generar reporte completo de inventario con lotes, ingresos y salidas")
    public ResponseEntity<List<ReporteInventarioDTO>> generarReporte() {
        return ResponseEntity.ok(inventarioService.generarReporte());
    }
}
