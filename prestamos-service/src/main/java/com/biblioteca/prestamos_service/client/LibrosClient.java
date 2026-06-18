package com.biblioteca.prestamos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "libros-service", url = "http://localhost:8081")
public interface LibrosClient {
    @GetMapping("/libros/{id}")
    Object buscarLibroPorId(@PathVariable String id);
   
}

