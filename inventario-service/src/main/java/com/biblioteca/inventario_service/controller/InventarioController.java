package com.biblioteca.inventario_service.controller;

import com.biblioteca.inventario_service.model.Inventario;
import com.biblioteca.inventario_service.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired private InventarioService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Inventario item) {
        return service.crear(item);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return service.listar();
    }

    @GetMapping("/libro/{libroId}")
    public List<Inventario> disponibles(@PathVariable String libroId) {
        return service.listarDisponiblesPorLibro(libroId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Inventario inventario) {
        return service.actualizar(id, inventario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}