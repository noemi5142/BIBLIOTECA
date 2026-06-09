package com.biblioteca.notificaciones_service.controller;

import com.biblioteca.notificaciones_service.model.Notificacion;
import com.biblioteca.notificaciones_service.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired private NotificacionService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Notificacion notificacion) {
        return service.crear(notificacion);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacion> porUsuario(@PathVariable String usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable String id) {
        return service.obtenerPorId(id);
    }
}