package com.biblioteca.reservas_service.controller;

import com.biblioteca.reservas_service.model.Reserva;
import com.biblioteca.reservas_service.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired private ReservaService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Reserva reserva) {
        return service.crear(reserva);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return service.listar();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> porUsuario(@PathVariable String usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable String id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @Valid @RequestBody Reserva reserva) {
        return service.actualizar(id, reserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminar(id);
    }
}