package com.biblioteca.reportes_service.controller;

import com.biblioteca.reportes_service.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")  // ← Esto es importante
public class ReporteController {

    @Autowired 
    private ReporteService service;

    @PostMapping("/generar")  // ← CORRECTO: @PostMapping
    public ResponseEntity<?> generar() {
        return service.generarReporte();
    }

    @GetMapping("/ultimo")
    public ResponseEntity<?> ultimo() {
        return service.obtenerUltimoReporte();
    }
}