package com.inventory.controller;

import com.inventory.dto.MovimientoDTO;
import com.inventory.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@Tag(name = "Movimientos", description = "Registro de Movimientos de Inventario (Ingreso y Salida)")
@CrossOrigin(origins = "*")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    @Operation(summary = "Listar todos los movimientos")
    public ResponseEntity<List<MovimientoDTO>> listarTodos() {
        return ResponseEntity.ok(movimientoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un movimiento por su ID")
    public ResponseEntity<MovimientoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(movimientoService.obtenerPorId(id));
    }

    @GetMapping("/producto/{idProducto}")
    @Operation(summary = "Listar movimientos por producto")
    public ResponseEntity<List<MovimientoDTO>> obtenerPorProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(movimientoService.obtenerPorProducto(idProducto));
    }

    @PostMapping
    @Operation(summary = "Registrar un movimiento (INGRESO o SALIDA). Actualiza automáticamente el stock.")
    public ResponseEntity<MovimientoDTO> registrar(@Valid @RequestBody MovimientoDTO dto) {
        MovimientoDTO registrado = movimientoService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrado);
    }
}
