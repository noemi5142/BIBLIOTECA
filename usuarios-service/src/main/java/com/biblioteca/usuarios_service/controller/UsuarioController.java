package com.biblioteca.usuarios_service.controller;

import com.biblioteca.usuarios_service.model.Usuario;
import com.biblioteca.usuarios_service.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // POST: Registrar usuario
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario) {
        return service.crearUsuario(usuario);
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<?> listar() {
        return service.listarUsuarios();
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    // PUT: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @Valid @RequestBody Usuario usuario) {
        return service.actualizarUsuario(id, usuario);
    }

    // DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminarUsuario(id);
    }
}