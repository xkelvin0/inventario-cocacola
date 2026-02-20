package com.inventory.web;

import com.inventory.dto.MovimientoDTO;
import com.inventory.dto.ProductoDTO;
import com.inventory.dto.InventarioDTO;
import com.inventory.dto.LoteDTO;
import com.inventory.entity.Movimiento.TipoMovimiento;
import com.inventory.service.MovimientoService;
import com.inventory.service.ProductoService;
import com.inventory.service.InventarioService;
import com.inventory.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/movimientos")
public class MovimientoWebController {

    @Autowired private MovimientoService movimientoService;
    @Autowired private ProductoService productoService;
    @Autowired private InventarioService inventarioService;
    @Autowired private LoteService loteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("movimientos", movimientoService.obtenerTodos());
        return "movimientos/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        List<ProductoDTO> productos = productoService.obtenerTodos();
        List<InventarioDTO> inventarios = inventarioService.obtenerTodos();
        model.addAttribute("movimiento", new MovimientoDTO());
        model.addAttribute("productos", productos);
        model.addAttribute("inventarios", inventarios);
        model.addAttribute("tipos", TipoMovimiento.values());
        return "movimientos/formulario";
    }

    @GetMapping("/lotes-por-inventario/{idInventario}")
    @ResponseBody
    public List<LoteDTO> lotesPorInventario(@PathVariable Integer idInventario) {
        // Obtener el idProducto a partir del inventario
        InventarioDTO inv = inventarioService.obtenerPorId(idInventario);
        return loteService.obtenerPorProducto(inv.getIdProducto());
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute MovimientoDTO movimientoDTO,
                          RedirectAttributes redirectAttributes) {
        try {
            movimientoDTO.setFecha(LocalDate.now());
            movimientoService.registrar(movimientoDTO);
            redirectAttributes.addFlashAttribute("exito", "Movimiento registrado. Stock actualizado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/movimientos";
    }
}
