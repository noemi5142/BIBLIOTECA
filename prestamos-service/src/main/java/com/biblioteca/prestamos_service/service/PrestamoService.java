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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        // 1. Validar usuario en usuarios-service
        try {
            Object usuario = usuarioClient.buscarUsuario(usuarioId);
            if (usuario == null) {
                return ResponseEntity.badRequest().body(
                    Map.of("mensaje", "No existe un usuario con el ID proporcionado")
                );
            }
        } catch (FeignException.NotFound e) {
            return ResponseEntity.badRequest().body(
                Map.of("mensaje", "No existe un usuario con el ID proporcionado")
            );
        }

        // 2. Validar stock en inventario-service
        try {
            Object inventario = inventarioClient.verificarDisponibilidad(libroId);
            if (inventario == null || ((List<?>) inventario).isEmpty()) {
                return ResponseEntity.badRequest().body(
                    Map.of("mensaje", "No hay copias disponibles del libro con el ID proporcionado")
                );
            }
        } catch (FeignException.NotFound e) {
            return ResponseEntity.badRequest().body(
                Map.of("mensaje", "No existe un libro con el ID proporcionado en el inventario")
            );
        }

        // 3. Si todo es válido, guardar en prestamos_db
        prestamo.setDevuelto(false);
        Prestamo guardado = repo.save(prestamo);
        
        return ResponseEntity.status(201).body(
            Map.of(
                "mensaje", "Préstamo registrado exitosamente",
                "prestamo", guardado
            )
        );

    } catch (FeignException e) {
        // Error genérico de conexión
        return ResponseEntity.status(503).body(
            Map.of("mensaje", "Error conectando con servicios externos. Intente más tarde.")
        );

    } catch (Exception e) {
        // Error interno
        return ResponseEntity.status(500).body(
            Map.of("mensaje", "Error interno del servidor")
        );
    }
}

    // GET: Buscar por ID
    public ResponseEntity<?> obtenerPorId(Long id) {
        Long idNoNulo = Objects.requireNonNull(id, "El id del préstamo no puede ser nulo");

        Prestamo prestamo = repo.findById(idNoNulo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + idNoNulo));
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Préstamo encontrado exitosamente");
        respuesta.put("prestamo", prestamo);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET: Listar todos
    public ResponseEntity<?> listarPrestamos() {
        List<Prestamo> prestamos = repo.findAll();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de préstamos obtenida exitosamente");
        respuesta.put("total", prestamos.size());
        respuesta.put("prestamos", prestamos);
        
        return ResponseEntity.ok(respuesta);
    }

    // PUT: Actualizar
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
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Préstamo actualizado exitosamente");
        respuesta.put("prestamo", actualizado);
        
        return ResponseEntity.ok(respuesta);
    }

    // DELETE: Eliminar
    public ResponseEntity<?> eliminar(Long id) {
        Long idNoNulo = Objects.requireNonNull(id, "El id del préstamo no puede ser nulo");
        if (!repo.existsById(idNoNulo)) {
            throw new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + idNoNulo);
        }
        repo.deleteById(idNoNulo);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Préstamo eliminado exitosamente");
        respuesta.put("idEliminado", idNoNulo);
        
        return ResponseEntity.ok(respuesta);
    }
}