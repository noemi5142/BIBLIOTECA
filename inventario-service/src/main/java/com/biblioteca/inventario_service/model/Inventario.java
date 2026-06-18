package com.biblioteca.inventario_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "inventario")
public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El ID del libro es obligatorio")
    private String libroId; 

    @NotBlank(message = "El código de copia es obligatorio")
    private String codigoCopia; 

    // CAMBIO AQUÍ: De EstadoLibro a String
    @NotBlank(message = "El estado es obligatorio")
    private String estado; 

    private String ubicacion; 

    public Inventario() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLibroId() { return libroId; }
    public void setLibroId(String libroId) { this.libroId = libroId; }
    
    public String getCodigoCopia() { return codigoCopia; }
    public void setCodigoCopia(String codigoCopia) { this.codigoCopia = codigoCopia; }
    
    // CAMBIO AQUÍ: Getter y Setter como String
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}
