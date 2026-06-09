package com.biblioteca.libros_service.service;

import com.biblioteca.libros_service.exception.RecursoNoEncontradoException;
import com.biblioteca.libros_service.model.Libro;
import com.biblioteca.libros_service.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LibroService {

    @Autowired
    private LibroRepository repo;

    // Crear libro
    public ResponseEntity<?> crearLibro(Libro libro) {
        Libro libroValido = Objects.requireNonNull(libro, "El libro no puede ser nulo");
        Libro guardado = repo.save(libroValido);
        return ResponseEntity.status(201).body(guardado);
    }

    // Listar todos
    public ResponseEntity<?> listarLibros() {
        List<Libro> libros = repo.findAll();
        return ResponseEntity.ok(libros);
    }

    // Buscar por ID
    public ResponseEntity<?> buscarPorId(String id) {
        String idValido = Objects.requireNonNull(id, "El id del libro no puede ser nulo");
        Libro libro = Objects.requireNonNull(
                repo.findById(idValido)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con ID: " + idValido)),
                "Libro no encontrado con ID: " + idValido
        );
        return ResponseEntity.ok(libro);
    }

    // Actualizar
    public ResponseEntity<?> actualizarLibro(String id, Libro libroActualizado) {
        String idValido = Objects.requireNonNull(id, "El id del libro no puede ser nulo");
        Libro libroValido = Objects.requireNonNull(libroActualizado, "El libro no puede ser nulo");

        Libro libroExistente = Objects.requireNonNull(
                repo.findById(idValido)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con ID: " + idValido)),
                "Libro no encontrado con ID: " + idValido
        );

        libroExistente.setTitulo(libroValido.getTitulo());
        libroExistente.setAutor(libroValido.getAutor());
        libroExistente.setStock(libroValido.getStock());

        Libro guardado = repo.save(libroExistente);
        return ResponseEntity.ok(guardado);
    }

    // Eliminar
    public ResponseEntity<?> eliminarLibro(String id) {
        String idValido = Objects.requireNonNull(id, "El id del libro no puede ser nulo");

        Libro libroExistente = Objects.requireNonNull(
                repo.findById(idValido)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con ID: " + idValido)),
                "Libro no encontrado con ID: " + idValido
        );

        repo.delete(libroExistente);
        return ResponseEntity.noContent().build();
    }
}