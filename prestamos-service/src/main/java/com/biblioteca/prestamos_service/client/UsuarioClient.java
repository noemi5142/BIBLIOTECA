package com.biblioteca.prestamos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-service", url = "http://localhost:8082")
public interface UsuarioClient {
    @GetMapping("/usuarios/{id}")
    Object buscarUsuario(@PathVariable String id);
}