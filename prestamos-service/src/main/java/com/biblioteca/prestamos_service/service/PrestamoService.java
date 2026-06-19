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

            // Verificar que el usuario existe
            Object usuario = usuarioClient.buscarUsuario(usuarioId);
            if (usuario == null) {
                return ResponseEntity.badRequest().body("Error: El usuario no existe");
            }

            // Verificar que hay copias disponibles
            List<Map<String, Object>> copias = inventarioClient.verificarDisponibilidad(libroId);
            if (copias == null || copias.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: No hay copias disponibles");
            }

            // Tomar la primera copia disponible
            Map<String, Object> copia = copias.get(0);
            Long inventarioId = Long.valueOf(copia.get("id").toString());

            // Guardar el préstamo
            prestamo.setDevuelto(false);
            Prestamo guardado = repo.save(prestamo);

            // Actualizar estado de la copia a PRESTADO
            Map<String, Object> actualizacion = new HashMap<>();
            actualizacion.put("libroId", copia.get("libroId"));
            actualizacion.put("codigoCopia", copia.get("codigoCopia"));
            actualizacion.put("estado", "PRESTADO");
            actualizacion.put("ubicacion", copia.get("ubicacion"));
            inventarioClient.actualizarInventario(inventarioId, actualizacion);

            return ResponseEntity.status(201).body(guardado);

        } catch (FeignException e) {
            return ResponseEntity.status(503).body("Error conectando con servicios externos: " + e.getMessage());
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

        boolean yaEstabaDevuelto = prestamoExistente.isDevuelto();

        if (prestamo.getFechaPrestamo() != null) {
            prestamoExistente.setFechaPrestamo(prestamo.getFechaPrestamo());
        }
        if (prestamo.isDevuelto() != yaEstabaDevuelto) {
            prestamoExistente.setDevuelto(prestamo.isDevuelto());
        }

        Prestamo actualizado = repo.save(prestamoExistente);

        // Si se está marcando como devuelto, actualizar inventario a DISPONIBLE
        if (prestamo.isDevuelto() && !yaEstabaDevuelto) {
            try {
                List<Map<String, Object>> copias = inventarioClient.obtenerInventarioPorLibro(
                        prestamoExistente.getLibroId()
                );
                if (copias != null && !copias.isEmpty()) {

                    Map<String, Object> copia = copias.stream()
                            .filter(c -> "PRESTADO".equals(c.get("estado")))
                            .findFirst()
                            .orElse(copias.get(0));

                    Long inventarioId = Long.valueOf(copia.get("id").toString());
                    Map<String, Object> actualizacion = new HashMap<>();
                    actualizacion.put("libroId", copia.get("libroId"));
                    actualizacion.put("codigoCopia", copia.get("codigoCopia"));
                    actualizacion.put("estado", "DISPONIBLE");
                    actualizacion.put("ubicacion", copia.get("ubicacion"));
                    inventarioClient.actualizarInventario(inventarioId, actualizacion);
                }
            } catch (FeignException e) {
                System.err.println("Advertencia: no se pudo actualizar el inventario: " + e.getMessage());
            }
        }

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