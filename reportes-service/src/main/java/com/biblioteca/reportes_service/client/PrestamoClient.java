package com.biblioteca.reportes_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "prestamos-service", url = "http://localhost:8086")
public interface PrestamoClient {
    @GetMapping("/prestamos")
    Object listarPrestamos();
}