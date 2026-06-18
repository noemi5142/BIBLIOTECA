package com.biblioteca.inventario_service.repository;

import com.biblioteca.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    
    boolean existsByCodigoCopia(String codigoCopia);
    
    // ✅ CAMBIO AQUÍ: El segundo parámetro ahora es String
    List<Inventario> findByLibroIdAndEstado(String libroId, String estado);
    
    List<Inventario> findByLibroId(String libroId);
}