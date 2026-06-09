package com.biblioteca.autores_service.repository;

import com.biblioteca.autores_service.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // JpaRepository ya incluye los métodos CRUD básicos
}