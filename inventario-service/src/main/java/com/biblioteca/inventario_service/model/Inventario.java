package com.biblioteca.inventario_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventario")
public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencia lógica al libros-service (MongoDB)
    @NotBlank(message = "El ID del libro es obligatorio")
    private String libroId; 

    @NotBlank(message = "El código de copia es obligatorio")
    private String codigoCopia; // Ej: "PRIN-001-A"

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING) // Guarda como texto en SQL
    private EstadoLibro estado;

    private String ubicacion; // Ej: "Estante A2"

    public Inventario() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLibroId() { return libroId; }
    public void setLibroId(String libroId) { this.libroId = libroId; }
    
    public String getCodigoCopia() { return codigoCopia; }
    public void setCodigoCopia(String codigoCopia) { this.codigoCopia = codigoCopia; }
    
    public EstadoLibro getEstado() { return estado; }
    public void setEstado(EstadoLibro estado) { this.estado = estado; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}