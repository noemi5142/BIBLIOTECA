package com.biblioteca.reservas_service.service;

import com.biblioteca.reservas_service.client.InventarioClient;
import com.biblioteca.reservas_service.exception.RecursoNoEncontradoException;
import com.biblioteca.reservas_service.model.Reserva;
import com.biblioteca.reservas_service.repository.ReservaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;



@Service
public class ReservaService {
    
    @Autowired private ReservaRepository repo;
    @Autowired private InventarioClient inventarioClient;

    public ResponseEntity<?> crear(Reserva reserva) {
        try {
            // Validar que haya copias disponibles en inventario-service
            Object inventario = inventarioClient.verificarDisponibilidad(reserva.getLibroId());
            if (inventario == null) {
                return ResponseEntity.badRequest().body("Error: No hay copias disponibles para reservar");
            }

            reserva.setActiva(true);
            Reserva guardada = repo.save(reserva);
            return ResponseEntity.status(201).body(guardada);

        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body("Error conectando con inventario-service");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    public List<Reserva> listarPorUsuario(String usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public ResponseEntity<?> obtenerPorId(String id) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la reserva no puede ser nulo");

        Reserva reserva = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con ID: " + idNoNulo));
        return ResponseEntity.ok(reserva);
    }

    public ResponseEntity<?> listar() {
        List<Reserva> todas = repo.findAll();
        return ResponseEntity.ok(todas);
    }

    public ResponseEntity<?> actualizar(String id, Reserva reserva) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la reserva no puede ser nulo");
        Reserva reservaExistente = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con ID: " + idNoNulo));

        if (reserva.isActiva() != reservaExistente.isActiva()) {
            reservaExistente.setActiva(reserva.isActiva());
        }
        if (reserva.getFechaSolicitud() != null) {
            reservaExistente.setFechaSolicitud(reserva.getFechaSolicitud());
        }

        Reserva actualizada = repo.save(reservaExistente);
        return ResponseEntity.ok(actualizada);
    }

    public ResponseEntity<?> eliminar(String id) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la reserva no puede ser nulo");
        if (!repo.existsById(idNoNulo)) {
            throw new RecursoNoEncontradoException("Reserva no encontrada con ID: " + idNoNulo);
        }
        repo.deleteById(idNoNulo);
        return ResponseEntity.ok("Reserva eliminada exitosamente");
    }
}