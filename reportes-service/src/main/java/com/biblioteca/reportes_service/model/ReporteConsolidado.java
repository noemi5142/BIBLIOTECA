package com.biblioteca.reportes_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "reportes_cache")
public class ReporteConsolidado {
    @Id 
    private String id;

    private int totalPrestamosActivos;
    private int totalMultasPendientes;
    private double montoTotalMultas;
    private LocalDateTime fechaGeneracion;

    public ReporteConsolidado() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public int getTotalPrestamosActivos() { return totalPrestamosActivos; }
    public void setTotalPrestamosActivos(int totalPrestamosActivos) { this.totalPrestamosActivos = totalPrestamosActivos; }
    
    public int getTotalMultasPendientes() { return totalMultasPendientes; }
    public void setTotalMultasPendientes(int totalMultasPendientes) { this.totalMultasPendientes = totalMultasPendientes; }
    
    public double getMontoTotalMultas() { return montoTotalMultas; }
    public void setMontoTotalMultas(double montoTotalMultas) { this.montoTotalMultas = montoTotalMultas; }
    
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
}