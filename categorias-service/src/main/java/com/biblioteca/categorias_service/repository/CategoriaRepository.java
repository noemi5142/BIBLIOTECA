package com.biblioteca.categorias_service.repository;

import com.biblioteca.categorias_service.model.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {
    Optional<Categoria> findByNombre(String nombre);
}