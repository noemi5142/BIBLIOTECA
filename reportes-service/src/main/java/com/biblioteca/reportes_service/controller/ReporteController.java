package com.biblioteca.reportes_service.controller;

import com.biblioteca.reportes_service.model.ReporteConsolidado;
import com.biblioteca.reportes_service.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired 
    private ReporteService service;

    @PostMapping("/generar")
    public ResponseEntity<?> generar() {
        return service.generarReporte();
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return service.listar();
    }

    @GetMapping("/ultimo")
    public ResponseEntity<?> ultimo() {
        return service.obtenerUltimoReporte();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable String id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @Valid @RequestBody ReporteConsolidado reporte) {
        return service.actualizar(id, reporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminar(id);
    }
}