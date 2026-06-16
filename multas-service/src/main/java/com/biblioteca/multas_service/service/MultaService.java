package com.biblioteca.multas_service.service;

import com.biblioteca.multas_service.client.PrestamoClient;
import com.biblioteca.multas_service.exception.RecursoNoEncontradoException;
import com.biblioteca.multas_service.model.Multa;
import com.biblioteca.multas_service.repository.MultaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MultaService {
    
    @Autowired private MultaRepository repo;
    @Autowired private PrestamoClient prestamoClient;

    public ResponseEntity<?> crear(Multa multa) {
        try {
            Multa multaNoNula = Objects.requireNonNull(multa, "La multa no puede ser nula");
            Long prestamoId = Objects.requireNonNull(multaNoNula.getPrestamoId(), "El campo prestamoId no puede ser nulo");

            // Validar que el préstamo exista en prestamos-service
            Object prestamo = prestamoClient.buscarPrestamo(prestamoId);
            if (prestamo == null) {
                return ResponseEntity.badRequest().body("Error: El préstamo no existe");
            }

            Multa guardada = repo.save(multaNoNula);
            return ResponseEntity.status(201).body(guardada);

        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body("Error conectando con prestamos-service");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    public List<Multa> listarPorPrestamo(Long prestamoId) {
        Long prestamoIdNoNulo = Objects.requireNonNull(prestamoId, "El id del préstamo no puede ser nulo");
        return repo.findByPrestamoId(prestamoIdNoNulo);
    }

    public ResponseEntity<?> obtenerPorId(Long id) {
        Long idNoNulo = Objects.requireNonNull(id, "El id de la multa no puede ser nulo");
        Multa multa = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Multa no encontrada con ID: " + idNoNulo));
        return ResponseEntity.ok(multa);
    }

    public ResponseEntity<?> listar() {
        List<Multa> todas = repo.findAll();
        return ResponseEntity.ok(todas);
    }

    public ResponseEntity<?> actualizar(Long id, Multa multa) {
        Long idNoNulo = Objects.requireNonNull(id, "El id de la multa no puede ser nulo");
        Multa multaExistente = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Multa no encontrada con ID: " + idNoNulo));

        if (multa.getMonto() != null) {
            multaExistente.setMonto(multa.getMonto());
        }
        if (multa.isPagada() != multaExistente.isPagada()) {
            multaExistente.setPagada(multa.isPagada());
        }
        if (multa.getRazon() != null && !multa.getRazon().isEmpty()) {
            multaExistente.setRazon(multa.getRazon());
        }

        Multa actualizada = repo.save(multaExistente);
        return ResponseEntity.ok(actualizada);
    }

    public ResponseEntity<?> eliminar(Long id) {
        Long idNoNulo = Objects.requireNonNull(id, "El id de la multa no puede ser nulo");
        if (!repo.existsById(idNoNulo)) {
            throw new RecursoNoEncontradoException("Multa no encontrada con ID: " + idNoNulo);
        }
        repo.deleteById(idNoNulo);
        return ResponseEntity.ok("Multa eliminada exitosamente");
    }
}