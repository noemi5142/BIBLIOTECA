package com.biblioteca.usuarios_service.service;

import com.biblioteca.usuarios_service.exception.RecursoNoEncontradoException;
import com.biblioteca.usuarios_service.model.Usuario;
import com.biblioteca.usuarios_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    // POST: Crear usuario
    public ResponseEntity<?> crearUsuario(Usuario usuario) {
        Usuario usuarioValido = Objects.requireNonNull(usuario, "El usuario no puede ser nulo");
        Usuario guardado = repo.save(usuarioValido);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario agregado exitosamente");
        respuesta.put("usuario", guardado);
        
        return ResponseEntity.status(201).body(respuesta);
    }

    // GET: Listar todos
    public ResponseEntity<?> listarUsuarios() {
        List<Usuario> usuarios = repo.findAll();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de usuarios obtenida exitosamente");
        respuesta.put("total", usuarios.size());
        respuesta.put("usuarios", usuarios);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET: Buscar por ID
    public ResponseEntity<?> buscarPorId(String id) {
        String idValido = Objects.requireNonNull(id, "El id del usuario no puede ser nulo");
        Usuario usuario = repo.findById(idValido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idValido));
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario encontrado exitosamente");
        respuesta.put("usuario", usuario);
        
        return ResponseEntity.ok(respuesta);
    }

    // PUT: Actualizar
    public ResponseEntity<?> actualizarUsuario(String id, Usuario usuarioActualizado) {
        String idValido = Objects.requireNonNull(id, "El id del usuario no puede ser nulo");
        Usuario usuarioValido = Objects.requireNonNull(usuarioActualizado, "El usuario no puede ser nulo");

        Usuario existente = repo.findById(idValido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idValido));

        existente.setNombre(usuarioValido.getNombre());
        existente.setCorreo(usuarioValido.getCorreo());
        existente.setTipoMembresia(usuarioValido.getTipoMembresia());

        Usuario guardado = repo.save(existente);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario actualizado exitosamente");
        respuesta.put("usuario", guardado);
        
        return ResponseEntity.ok(respuesta);
    }

    // DELETE: Eliminar
    public ResponseEntity<?> eliminarUsuario(String id) {
        String idValido = Objects.requireNonNull(id, "El id del usuario no puede ser nulo");

        Usuario existente = repo.findById(idValido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idValido));

        repo.delete(existente);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario eliminado exitosamente");
        respuesta.put("idEliminado", idValido);
        
        return ResponseEntity.ok(respuesta);
    }
}