package com.biblioteca.autores_service.controller;

import com.biblioteca.autores_service.model.Autor;
import com.biblioteca.autores_service.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    // POST: Crear autor
    @PostMapping
    public ResponseEntity<Autor> crear(@Valid @RequestBody Autor autor) {
        return service.crearAutor(autor);
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<List<Autor>> listar() {
        return service.listarAutores();
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // PUT: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizar(@PathVariable Long id, @Valid @RequestBody Autor autor) {
        return service.actualizarAutor(id, autor);
    }

    // DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        return service.eliminarAutor(id);
    }
}