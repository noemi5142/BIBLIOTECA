package com.biblioteca.prestamos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "libros-service", url = "http://localhost:8081")
public interface LibrosClient {
    @GetMapping("/libros/{libroId}")
    Object obtenerLibro(@PathVariable String libroId);
    @GetMapping("/libros/disponibilidad/{libroId}")
    object varificarDisponibilidadLibro(@PathVariable String libroId);
}
