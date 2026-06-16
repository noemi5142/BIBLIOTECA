package com.biblioteca.prestamos_service.service;

import com.biblioteca.prestamos_service.client.InventarioClient;
import com.biblioteca.prestamos_service.client.UsuarioClient;
import com.biblioteca.prestamos_service.exception.RecursoNoEncontradoException;
import com.biblioteca.prestamos_service.model.Prestamo;
import com.biblioteca.prestamos_service.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import feign.FeignException;

import java.util.Objects;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository repo;

    @Autowired
    private UsuarioClient usuarioClient;
    
    @Autowired
    private InventarioClient inventarioClient;

    public ResponseEntity<?> crear(Prestamo prestamo) {
        try {
            String usuarioId = Objects.requireNonNull(prestamo.getUsuarioId(), "El usuarioId es obligatorio");
            String libroId = Objects.requireNonNull(prestamo.getLibroId(), "El libroId es obligatorio");

            // 1. Validar usuario en usuarios-service (Clase 12)
            Object usuario = usuarioClient.buscarUsuario(usuarioId);
            if (usuario == null) {
                return ResponseEntity.badRequest().body("Error: El usuario no existe");
            }

            // 2. Validar stock en inventario-service (Clase 12)
            Object inventario = inventarioClient.verificarDisponibilidad(libroId);
            if (inventario == null) {
                return ResponseEntity.badRequest().body("Error: No hay copias disponibles");
            }

            // 3. Si todo es válido, guardar en prestamos_db (Clase 13)
            prestamo.setDevuelto(false);
            Prestamo guardado = repo.save(prestamo);
            return ResponseEntity.status(201).body(guardado);

        } catch (FeignException e) {
            // Captura fallos de red o servicios caídos (Clase 11)
            return ResponseEntity.status(e.status()).body("Error conectando con servicios externos");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerPorId(Long id) {
        Long idNoNulo = Objects.requireNonNull(id, "El id del préstamo no puede ser nulo");

        Prestamo prestamo = repo.findById(idNoNulo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + idNoNulo));
        return ResponseEntity.ok(prestamo);
    }

    public ResponseEntity<?> listarPrestamos() {
        return ResponseEntity.ok(repo.findAll());
    }

    public ResponseEntity<?> actualizar(Long id, Prestamo prestamo) {
        Long idNoNulo = Objects.requireNonNull(id, "El id del préstamo no puede ser nulo");
        Prestamo prestamoExistente = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + idNoNulo));

        if (prestamo.getFechaPrestamo() != null) {
            prestamoExistente.setFechaPrestamo(prestamo.getFechaPrestamo());
        }
        if (prestamo.isDevuelto() != prestamoExistente.isDevuelto()) {
            prestamoExistente.setDevuelto(prestamo.isDevuelto());
        }

        Prestamo actualizado = repo.save(prestamoExistente);
        return ResponseEntity.ok(actualizado);
    }

    public ResponseEntity<?> eliminar(Long id) {
        Long idNoNulo = Objects.requireNonNull(id, "El id del préstamo no puede ser nulo");
        if (!repo.existsById(idNoNulo)) {
            throw new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + idNoNulo);
        }
        repo.deleteById(idNoNulo);
        return ResponseEntity.ok("Préstamo eliminado exitosamente");
    }
}