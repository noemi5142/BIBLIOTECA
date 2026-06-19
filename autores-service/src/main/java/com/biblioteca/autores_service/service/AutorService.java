package com.biblioteca.autores_service.service;

import com.biblioteca.autores_service.exception.RecursoNoEncontradoException;
import com.biblioteca.autores_service.model.Autor;
import com.biblioteca.autores_service.repository.AutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AutorService {

    private final AutorRepository repo;

    public AutorService(AutorRepository repo) {
        this.repo = Objects.requireNonNull(repo, "AutorRepository no puede ser null");
    }

    // POST: Crear autor
    public ResponseEntity<?> crearAutor(Autor autor) {
        Autor guardado = repo.save(autor);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Autor agregado exitosamente");
        respuesta.put("autor", guardado);
        
        return ResponseEntity.status(201).body(respuesta);
    }

    // GET: Listar todos
    public ResponseEntity<?> listarAutores() {
        List<Autor> autores = repo.findAll();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de autores obtenida exitosamente");
        respuesta.put("total", autores.size());
        respuesta.put("autores", autores);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET: Buscar por ID
    public ResponseEntity<?> buscarPorId(Long id) {
        Autor autor = obtenerAutorOExcepcion(id);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Autor encontrado exitosamente");
        respuesta.put("autor", autor);
        
        return ResponseEntity.ok(respuesta);
    }

    // PUT: Actualizar
    public ResponseEntity<?> actualizarAutor(Long id, Autor autorActualizado) {
        Autor existente = obtenerAutorOExcepcion(id);

        existente.setNombre(autorActualizado.getNombre());
        existente.setNacionalidad(autorActualizado.getNacionalidad());

        Autor guardado = repo.save(existente);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Autor actualizado exitosamente");
        respuesta.put("autor", guardado);
        
        return ResponseEntity.ok(respuesta);
    }

    // DELETE: Eliminar
    public ResponseEntity<?> eliminarAutor(Long id) {
        Autor existente = obtenerAutorOExcepcion(id);
        repo.delete(existente);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Autor eliminado exitosamente");
        respuesta.put("idEliminado", id);
        respuesta.put("nombreAutor", existente.getNombre());
        
        return ResponseEntity.ok(respuesta);
    }

    private Autor obtenerAutorOExcepcion(Long id) {
        Objects.requireNonNull(id, "El id no puede ser null");
        return Objects.requireNonNull(
                repo.findById(id)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con ID: " + id)),
                "Autor no puede ser null"
        );
    }
}