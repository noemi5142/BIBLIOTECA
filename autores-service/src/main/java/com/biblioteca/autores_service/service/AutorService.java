package com.biblioteca.autores_service.service;

import com.biblioteca.autores_service.exception.RecursoNoEncontradoException;
import com.biblioteca.autores_service.model.Autor;
import com.biblioteca.autores_service.repository.AutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AutorService {

    private final AutorRepository repo;

    public AutorService(AutorRepository repo) {
        this.repo = Objects.requireNonNull(repo, "AutorRepository no puede ser null");
    }

    public ResponseEntity<Autor> crearAutor(Autor autor) {
        Autor guardado = repo.save(autor);
        return ResponseEntity.status(201).body(guardado);
    }

    public ResponseEntity<List<Autor>> listarAutores() {
        List<Autor> autores = repo.findAll();
        return ResponseEntity.ok(autores);
    }

    public ResponseEntity<Autor> buscarPorId(Long id) {
        Autor autor = obtenerAutorOExcepcion(id);
        return ResponseEntity.ok(autor);
    }

    public ResponseEntity<Autor> actualizarAutor(Long id, Autor autorActualizado) {
        Autor existente = obtenerAutorOExcepcion(id);

        existente.setNombre(autorActualizado.getNombre());
        existente.setNacionalidad(autorActualizado.getNacionalidad());

        Autor guardado = repo.save(existente);
        return ResponseEntity.ok(guardado);
    }

    public ResponseEntity<String> eliminarAutor(Long id) {
        Autor existente = obtenerAutorOExcepcion(id);
        repo.delete(existente);
        return ResponseEntity.ok("Autor eliminado exitosamente");
    }

    private Autor obtenerAutorOExcepcion(Long id) {
        Objects.requireNonNull(id, "El id no puede ser null");
        return Objects.requireNonNull(
                repo.findById(id)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con ID: " + id)),
                "Autor no puede ser null"
        );
    }
}