package com.inventory.web;

import com.inventory.dto.ProductoDTO;
import com.inventory.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoWebController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String nombre,
                         @RequestParam(required = false) String categoria,
                         @RequestParam(required = false) Boolean estado) {
        List<ProductoDTO> productos;
        if (nombre != null && !nombre.isBlank()) {
            productos = productoService.buscarPorNombre(nombre);
        } else if (categoria != null && !categoria.isBlank()) {
            productos = productoService.buscarPorCategoria(categoria);
        } else if (estado != null) {
            productos = productoService.obtenerPorEstado(estado);
        } else {
            productos = productoService.obtenerTodos();
        }
        model.addAttribute("productos", productos);
        model.addAttribute("filtroNombre", nombre);
        model.addAttribute("filtroCategoria", categoria);
        model.addAttribute("filtroEstado", estado);
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("producto", new ProductoDTO());
        model.addAttribute("titulo", "Registrar Producto");
        model.addAttribute("accion", "/productos/guardar");
        
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ProductoDTO productoDTO,
                          RedirectAttributes redirectAttributes) {
        try {
            productoService.crear(productoDTO);
            redirectAttributes.addFlashAttribute("exito", "Producto registrado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        ProductoDTO producto = productoService.obtenerPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        model.addAttribute("accion", "/productos/actualizar/" + id);
        return "productos/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute ProductoDTO productoDTO,
                             RedirectAttributes redirectAttributes) {
        try {
            productoService.actualizar(id, productoDTO);
            redirectAttributes.addFlashAttribute("exito", "Producto actualizado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Producto eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/productos";
    }
}
