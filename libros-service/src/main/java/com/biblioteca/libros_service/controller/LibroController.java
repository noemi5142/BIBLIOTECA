package com.biblioteca.libros_service.controller;

import com.biblioteca.libros_service.model.Libro;
import com.biblioteca.libros_service.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService service;

    // POST: Crear libro
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Libro libro) {
        return service.crearLibro(libro);
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<?> listar() {
        return service.listarLibros();
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    // PUT: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @Valid @RequestBody Libro libro) {
        return service.actualizarLibro(id, libro);
    }

    // DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminarLibro(id);
    }
}