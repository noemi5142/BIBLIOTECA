package com.biblioteca.notificaciones_service.repository;

import com.biblioteca.notificaciones_service.model.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificacionRepository extends MongoRepository<Notificacion, String> {
    List<Notificacion> findByUsuarioId(String usuarioId);
    List<Notificacion> findByTipo(String tipo);
}