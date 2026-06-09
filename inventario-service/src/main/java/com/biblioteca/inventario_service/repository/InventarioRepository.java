package com.biblioteca.inventario_service.repository;

import com.biblioteca.inventario_service.model.Inventario;
import com.biblioteca.inventario_service.model.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    // Consulta automática JPA para filtrar por libro y estado
    List<Inventario> findByLibroIdAndEstado(String libroId, EstadoLibro estado);
    
    // Validación de unicidad de código de copia
    boolean existsByCodigoCopia(String codigoCopia);
}