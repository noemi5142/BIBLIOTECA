package com.biblioteca.reportes_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "multas-service", url = "http://localhost:8088")
public interface MultaClient {
    @GetMapping("/multas")
    Object listarMultas();
}