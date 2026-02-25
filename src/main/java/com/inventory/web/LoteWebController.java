package com.inventory.web;

import com.inventory.dto.LoteDTO;
import com.inventory.dto.ProductoDTO;
import com.inventory.service.LoteService;
import com.inventory.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/lotes")
public class LoteWebController {

    @Autowired
    private LoteService loteService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lotes", loteService.obtenerTodos());
        return "lotes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        List<ProductoDTO> productos = productoService.obtenerTodos();
        model.addAttribute("lote", new LoteDTO());
        model.addAttribute("productos", productos);
        return "lotes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute LoteDTO loteDTO,
                          RedirectAttributes redirectAttributes) {
        try {
            loteService.crear(loteDTO);
            redirectAttributes.addFlashAttribute("exito", "Lote registrado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/lotes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            loteService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Lote eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/lotes";
    }
}
