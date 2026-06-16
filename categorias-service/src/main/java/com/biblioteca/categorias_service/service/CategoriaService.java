package com.biblioteca.categorias_service.service;

import com.biblioteca.categorias_service.exception.RecursoNoEncontradoException;
import com.biblioteca.categorias_service.model.Categoria;
import com.biblioteca.categorias_service.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoriaService {
    
    @Autowired private CategoriaRepository repo;

    public ResponseEntity<?> crear(Categoria categoria) {
        Categoria categoriaNoNula = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
        Categoria guardada = repo.save(categoriaNoNula);
        return ResponseEntity.status(201).body(guardada);
    }

    public List<Categoria> listarTodas() {
        return repo.findAll();
    }

    public ResponseEntity<?> obtenerPorId(String id) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la categoría no puede ser nulo");
        Categoria categoria = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + idNoNulo));
        return ResponseEntity.ok(categoria);
    }

    public ResponseEntity<?> actualizar(String id, Categoria categoria) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la categoría no puede ser nulo");
        Categoria categoriaExistente = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + idNoNulo));
        
        if (categoria.getNombre() != null && !categoria.getNombre().isEmpty()) {
            categoriaExistente.setNombre(categoria.getNombre());
        }
        if (categoria.getDescripcion() != null && !categoria.getDescripcion().isEmpty()) {
            categoriaExistente.setDescripcion(categoria.getDescripcion());
        }
        
        Categoria actualizada = repo.save(categoriaExistente);
        return ResponseEntity.ok(actualizada);
    }

    public ResponseEntity<?> eliminar(String id) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la categoría no puede ser nulo");
        if (!repo.existsById(idNoNulo)) {
            throw new RecursoNoEncontradoException("Categoría no encontrada con ID: " + idNoNulo);
        }
        repo.deleteById(idNoNulo);
        return ResponseEntity.ok("Categoría eliminada exitosamente");
    }
}