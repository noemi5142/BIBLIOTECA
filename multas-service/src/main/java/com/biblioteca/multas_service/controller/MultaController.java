package com.biblioteca.multas_service.controller;

import com.biblioteca.multas_service.model.Multa;
import com.biblioteca.multas_service.service.MultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/multas")
public class MultaController {

    @Autowired private MultaService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Multa multa) {
        return service.crear(multa);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return service.listar();
    }

    @GetMapping("/prestamo/{prestamoId}")
    public List<Multa> porPrestamo(@PathVariable Long prestamoId) {
        return service.listarPorPrestamo(prestamoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Multa multa) {
        return service.actualizar(id, multa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}