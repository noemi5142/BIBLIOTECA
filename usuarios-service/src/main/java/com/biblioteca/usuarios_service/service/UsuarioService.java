package com.biblioteca.usuarios_service.service;

import com.biblioteca.usuarios_service.exception.RecursoNoEncontradoException;
import com.biblioteca.usuarios_service.model.Usuario;
import com.biblioteca.usuarios_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    // POST: Crear usuario
    public ResponseEntity<?> crearUsuario(Usuario usuario) {
        Usuario usuarioValido = Objects.requireNonNull(usuario, "El usuario no puede ser nulo");
        Usuario guardado = repo.save(usuarioValido);
        return ResponseEntity.status(201).body(guardado);
    }

    // GET: Listar todos
    public ResponseEntity<?> listarUsuarios() {
        List<Usuario> usuarios = repo.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // GET: Buscar por ID
    public ResponseEntity<?> buscarPorId(String id) {
        String idValido = Objects.requireNonNull(id, "El id del usuario no puede ser nulo");
        Usuario usuario = Objects.requireNonNull(
                repo.findById(idValido)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idValido)),
                "Usuario no encontrado con ID: " + idValido
        );
        return ResponseEntity.ok(usuario);
    }

    // PUT: Actualizar
    public ResponseEntity<?> actualizarUsuario(String id, Usuario usuarioActualizado) {
        String idValido = Objects.requireNonNull(id, "El id del usuario no puede ser nulo");
        Usuario usuarioValido = Objects.requireNonNull(usuarioActualizado, "El usuario no puede ser nulo");

        Usuario existente = Objects.requireNonNull(
                repo.findById(idValido)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idValido)),
                "Usuario no encontrado con ID: " + idValido
        );

        existente.setNombre(usuarioValido.getNombre());
        existente.setCorreo(usuarioValido.getCorreo());
        existente.setTipoMembresia(usuarioValido.getTipoMembresia());

        Usuario guardado = repo.save(existente);
        return ResponseEntity.ok(guardado);
    }

    // DELETE: Eliminar
    public ResponseEntity<?> eliminarUsuario(String id) {
        String idValido = Objects.requireNonNull(id, "El id del usuario no puede ser nulo");

        Usuario existente = Objects.requireNonNull(
                repo.findById(idValido)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idValido)),
                "Usuario no encontrado con ID: " + idValido
        );

        repo.delete(existente);
        return ResponseEntity.noContent().build();
    }
}