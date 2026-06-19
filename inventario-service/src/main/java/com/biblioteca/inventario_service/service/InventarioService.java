package com.biblioteca.inventario_service.service;

import com.biblioteca.inventario_service.exception.RecursoNoEncontradoException;
import com.biblioteca.inventario_service.model.Inventario;
import com.biblioteca.inventario_service.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class InventarioService {
    
    @Autowired private InventarioRepository repo;

    public ResponseEntity<?> crear(Inventario item) {
        Inventario itemNoNulo = Objects.requireNonNull(item, "El item de inventario no puede ser nulo");
        String codigoCopia = Objects.requireNonNull(itemNoNulo.getCodigoCopia(), "El código de copia no puede ser nulo");

        if (repo.existsByCodigoCopia(codigoCopia)) {
            return ResponseEntity.badRequest().body("Error: Ya existe una copia con ese código");
        }
        
        Inventario guardado = repo.save(itemNoNulo);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Item de inventario agregado exitosamente");
        respuesta.put("inventario", guardado);
        
        return ResponseEntity.status(201).body(respuesta);
    }

    public List<Inventario> listarDisponiblesPorLibro(String libroId) {
        return repo.findByLibroIdAndEstado(libroId, "DISPONIBLE");
    }

    public ResponseEntity<?> obtenerPorId(Long id) {
        Long idNoNulo = Objects.requireNonNull(id, "El id del item de inventario no puede ser nulo");
        Inventario item = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Item de inventario no encontrado"));
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Item de inventario encontrado exitosamente");
        respuesta.put("inventario", item);
        
        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<?> listar() {
        List<Inventario> todos = repo.findAll();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de items de inventario obtenida exitosamente");
        respuesta.put("total", todos.size());
        respuesta.put("inventario", todos);
        
        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<?> actualizar(Long id, Inventario inventario) {
        Long idNoNulo = Objects.requireNonNull(id, "El id del item de inventario no puede ser nulo");
        Inventario itemExistente = repo.findById(idNoNulo)
            .orElseThrow(() -> new RecursoNoEncontradoException("Item de inventario no encontrado"));

        if (inventario.getEstado() != null) {
            itemExistente.setEstado(inventario.getEstado());
        }
        if (inventario.getUbicacion() != null && !inventario.getUbicacion().isEmpty()) {
            itemExistente.setUbicacion(inventario.getUbicacion());
        }

        Inventario actualizado = repo.save(itemExistente);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Item de inventario actualizado exitosamente");
        respuesta.put("inventario", actualizado);
        
        return ResponseEntity.ok(respuesta);
    }

    public ResponseEntity<?> eliminar(Long id) {
        Long idNoNulo = Objects.requireNonNull(id, "El id del item de inventario no puede ser nulo");
        if (!repo.existsById(idNoNulo)) {
            throw new RecursoNoEncontradoException("Item de inventario no encontrado");
        }
        repo.deleteById(idNoNulo);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Item de inventario eliminado exitosamente");
        respuesta.put("idEliminado", idNoNulo);
        
        return ResponseEntity.ok(respuesta);
    }
}