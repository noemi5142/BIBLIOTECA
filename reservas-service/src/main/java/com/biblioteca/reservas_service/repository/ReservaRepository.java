package com.biblioteca.reservas_service.repository;

import com.biblioteca.reservas_service.model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReservaRepository extends MongoRepository<Reserva, String> {
    List<Reserva> findByUsuarioId(String usuarioId);
    List<Reserva> findByLibroIdAndActivaTrue(String libroId);
}