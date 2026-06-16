package com.biblioteca.notificaciones_service.service;

import com.biblioteca.notificaciones_service.exception.RecursoNoEncontradoException;
import com.biblioteca.notificaciones_service.model.Notificacion;
import com.biblioteca.notificaciones_service.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class NotificacionService {
    
    @Autowired private NotificacionRepository repo;

    public ResponseEntity<?> crear(Notificacion notificacion) {
        Notificacion notif = Objects.requireNonNull(notificacion, "La notificación no puede ser nula");

        // Lógica de negocio: Establecer metadata automática
        notif.setFechaEnvio(LocalDateTime.now());
        notif.setLeida(false);
        
        Notificacion guardada = repo.save(notif);
        return ResponseEntity.status(201).body(guardada);
    }

    public List<Notificacion> listarPorUsuario(String usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public ResponseEntity<?> obtenerPorId(String id) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la notificación no puede ser nulo");

        Notificacion notif = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Notificación no encontrada con ID: " + idNoNulo));
        return ResponseEntity.ok(notif);
    }

    public ResponseEntity<?> listar() {
        List<Notificacion> todas = repo.findAll();
        return ResponseEntity.ok(todas);
    }

    public ResponseEntity<?> actualizar(String id, Notificacion notificacion) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la notificación no puede ser nulo");
        Notificacion notifExistente = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Notificación no encontrada con ID: " + idNoNulo));

        if (notificacion.getMensaje() != null && !notificacion.getMensaje().isEmpty()) {
            notifExistente.setMensaje(notificacion.getMensaje());
        }
        if (notificacion.isLeida() != notifExistente.isLeida()) {
            notifExistente.setLeida(notificacion.isLeida());
        }

        Notificacion actualizada = repo.save(notifExistente);
        return ResponseEntity.ok(actualizada);
    }

    public ResponseEntity<?> eliminar(String id) {
        String idNoNulo = Objects.requireNonNull(id, "El id de la notificación no puede ser nulo");
        if (!repo.existsById(idNoNulo)) {
            throw new RecursoNoEncontradoException("Notificación no encontrada con ID: " + idNoNulo);
        }
        repo.deleteById(idNoNulo);
        return ResponseEntity.ok("Notificación eliminada exitosamente");
    }
}