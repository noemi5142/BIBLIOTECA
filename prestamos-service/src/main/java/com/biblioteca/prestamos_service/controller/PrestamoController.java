package com.biblioteca.prestamos_service.controller;

import com.biblioteca.prestamos_service.model.Prestamo;
import com.biblioteca.prestamos_service.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired private PrestamoService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Prestamo prestamo) {
        return service.crear(prestamo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }
}