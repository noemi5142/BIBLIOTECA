package com.biblioteca.prestamos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "libros-service", url = "http://localhost:8085")
public interface LibrosClient {
    @GetMapping("/libros/libro/{libroId}")
    Object verificarDisponibilidad(@PathVariable String libroId);
}
