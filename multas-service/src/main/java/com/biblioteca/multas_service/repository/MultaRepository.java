package com.biblioteca.multas_service.repository;

import com.biblioteca.multas_service.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MultaRepository extends JpaRepository<Multa, Long> {
    List<Multa> findByPrestamoId(Long prestamoId);
    List<Multa> findByPagadaFalse();
}