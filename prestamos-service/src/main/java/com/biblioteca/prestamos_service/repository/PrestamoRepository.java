package com.biblioteca.prestamos_service.repository;

import com.biblioteca.prestamos_service.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {}