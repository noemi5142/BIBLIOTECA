package com.biblioteca.prestamos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "libros-service", url = "http://host.docker.internal:8081")
public interface LibroClient {
    
    @GetMapping("/libros/{id}")
    Object buscarLibro(@PathVariable String id);
}