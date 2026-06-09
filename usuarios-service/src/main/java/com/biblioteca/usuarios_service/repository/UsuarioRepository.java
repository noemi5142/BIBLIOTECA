package com.biblioteca.usuarios_service.repository;
import com.biblioteca.usuarios_service.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    
}