package com.biblioteca.reportes_service.repository;

import com.biblioteca.reportes_service.model.ReporteConsolidado;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReporteRepository extends MongoRepository<ReporteConsolidado, String> {
}