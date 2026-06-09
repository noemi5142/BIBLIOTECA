package com.biblioteca.reportes_service.service;

import com.biblioteca.reportes_service.client.MultaClient;
import com.biblioteca.reportes_service.client.PrestamoClient;
import com.biblioteca.reportes_service.model.ReporteConsolidado;
import com.biblioteca.reportes_service.repository.ReporteRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReporteService {
    
    @Autowired private ReporteRepository repo;
    @Autowired private PrestamoClient prestamoClient;
    @Autowired private MultaClient multaClient;

    public ResponseEntity<?> generarReporte() {
        try {
            List<?> prestamos = (List<?>) prestamoClient.listarPrestamos();
            List<?> multas = (List<?>) multaClient.listarMultas();

            ReporteConsolidado reporte = new ReporteConsolidado();
            reporte.setTotalPrestamosActivos(prestamos != null ? prestamos.size() : 0);
            reporte.setTotalMultasPendientes(multas != null ? multas.size() : 0);
            reporte.setMontoTotalMultas(0.0);
            reporte.setFechaGeneracion(LocalDateTime.now());

            ReporteConsolidado guardado = repo.save(reporte);
            return ResponseEntity.ok(guardado);

        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body("Error obteniendo datos para reporte");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno generando reporte");
        }
    }

    public ResponseEntity<?> obtenerUltimoReporte() {
        return repo.findAll().stream()
            .max((r1, r2) -> r1.getFechaGeneracion().compareTo(r2.getFechaGeneracion()))
            .<ResponseEntity<?>>map(r -> ResponseEntity.ok(r))
            .orElseGet(() -> ResponseEntity.status(404)
                .body((Object) Map.of("mensaje", "No hay reportes generados")));
    }
}