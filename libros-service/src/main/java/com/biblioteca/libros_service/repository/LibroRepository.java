package com.biblioteca.libros_service.repository;

import com.biblioteca.libros_service.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends MongoRepository<Libro, String> {
    // MongoRepository ya incluye: findAll(), findById(), save(), deleteById()
}