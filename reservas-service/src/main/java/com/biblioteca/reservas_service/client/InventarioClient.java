package com.biblioteca.reservas_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventario-service", url = "http://localhost:8085")
public interface InventarioClient {
    @GetMapping("/inventario/libro/{libroId}")
    Object verificarDisponibilidad(@PathVariable String libroId);
}