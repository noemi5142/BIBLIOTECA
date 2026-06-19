package com.biblioteca.autores_service.controller;

import com.biblioteca.autores_service.model.Autor;
import com.biblioteca.autores_service.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    // POST: Crear autor
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Autor autor) { // ✅ Cambiado a <?>
        return service.crearAutor(autor);
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<?> listar() { // ✅ Cambiado a <?>
        return service.listarAutores();
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) { // ✅ Cambiado a <?>
        return service.buscarPorId(id);
    }

    // PUT: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Autor autor) { // ✅ Cambiado a <?>
        return service.actualizarAutor(id, autor);
    }

    // DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) { // ✅ Cambiado a <?>
        return service.eliminarAutor(id);
    }
}