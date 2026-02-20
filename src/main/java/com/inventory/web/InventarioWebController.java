package com.inventory.web;

import com.inventory.service.InventarioService;
import com.inventory.service.ProductoService;
import com.inventory.dto.InventarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventario")
public class InventarioWebController {

    @Autowired private InventarioService inventarioService;
    @Autowired private ProductoService productoService;

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) Integer idProducto) {
        List<InventarioDTO> inventarios;
        if (idProducto != null) {
            try {
                InventarioDTO inv = inventarioService.obtenerPorProducto(idProducto);
                inventarios = inv != null ? List.of(inv) : List.of();
            } catch (Exception e) {
                inventarios = List.of();
            }
        } else {
            inventarios = inventarioService.obtenerTodos();
        }
        model.addAttribute("inventarios", inventarios);
        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("productoSeleccionado", idProducto);
        return "inventario/lista";
    }

    @GetMapping("/reporte")
    public String reporte(Model model) {
        model.addAttribute("reporte", inventarioService.generarReporte());
        return "inventario/reporte";
    }
}
