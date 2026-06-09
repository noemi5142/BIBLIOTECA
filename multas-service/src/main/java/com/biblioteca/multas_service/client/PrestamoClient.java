package com.biblioteca.multas_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "prestamos-service", url = "http://localhost:8086")
public interface PrestamoClient {
    @GetMapping("/prestamos/{id}")
    Object buscarPrestamo(@PathVariable Long id);
}