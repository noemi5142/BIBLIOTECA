package com.biblioteca.prestamos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
    
    @FeignClient(name = "inventario-service", url = "http://host.docker.internal:8085")
    public interface InventarioClient {
        
    @GetMapping("/inventario/libro/{libroId}")
    List<Map<String, Object>> verificarDisponibilidad(@PathVariable String libroId);

    @GetMapping("/inventario/libro/{libroId}")
    List<Map<String, Object>> obtenerInventarioPorLibro(@PathVariable String libroId);

    @PutMapping("/inventario/{id}")
    Map<String, Object> actualizarInventario(
            @PathVariable Long id,
            @RequestBody Map<String, Object> inventario
    );
}