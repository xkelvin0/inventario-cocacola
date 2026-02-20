package com.inventory.controller;

import com.inventory.dto.LoteDTO;
import com.inventory.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lotes")
@Tag(name = "Lotes", description = "Gestión de Lotes por Producto")
@CrossOrigin(origins = "*")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @GetMapping
    @Operation(summary = "Listar todos los lotes")
    public ResponseEntity<List<LoteDTO>> listarTodos() {
        return ResponseEntity.ok(loteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un lote por su ID")
    public ResponseEntity<LoteDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(loteService.obtenerPorId(id));
    }

    @GetMapping("/producto/{idProducto}")
    @Operation(summary = "Listar lotes por producto")
    public ResponseEntity<List<LoteDTO>> obtenerPorProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(loteService.obtenerPorProducto(idProducto));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo lote para un producto")
    public ResponseEntity<LoteDTO> crear(@Valid @RequestBody LoteDTO dto) {
        LoteDTO creado = loteService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un lote por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        loteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
