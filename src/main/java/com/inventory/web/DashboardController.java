package com.inventory.web;

import com.inventory.service.InventarioService;
import com.inventory.service.ProductoService;
import com.inventory.dto.InventarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String dashboard(Model model) {
        List<InventarioDTO> inventarios = inventarioService.obtenerTodos();
        List<InventarioDTO> bajoStock = inventarios.stream()
                .filter(i -> i.getStockActual() != null && i.getStockActual() < 10)
                .collect(Collectors.toList());

        int totalStock = inventarios.stream()
                .mapToInt(i -> i.getStockActual() != null ? i.getStockActual() : 0)
                .sum();

        model.addAttribute("inventarios", inventarios);
        model.addAttribute("bajoStock", bajoStock);
        model.addAttribute("totalProductos", inventarios.size());
        model.addAttribute("totalStock", totalStock);
        model.addAttribute("alertas", bajoStock.size());
        
        // Métricas Ingeniería
        model.addAttribute("valorizacionTotal", inventarioService.obtenerValorizacionTotal());
        model.addAttribute("lotesCriticos", inventarioService.obtenerConteoLotesCriticos());
        model.addAttribute("topMovimientos", inventarioService.obtenerTopMovimientos());
        
        return "dashboard";
    }
}
