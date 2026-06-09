package com.biblioteca.notificaciones_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Document(collection = "notificaciones")
public class Notificacion {
    @Id 
    private String id;

    @NotBlank(message = "El ID del usuario destino es obligatorio")
    private String usuarioId;

    @NotBlank(message = "El tipo de notificación es obligatorio")
    private String tipo; // Ej: "RECORDATORIO_DEVOLUCION", "CONFIRMACION_PRESTAMO"

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    private LocalDateTime fechaEnvio;
    private boolean leida;

    public Notificacion() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    
    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }
}