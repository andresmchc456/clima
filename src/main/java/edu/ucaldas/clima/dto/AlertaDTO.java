package edu.ucaldas.clima.dto;

import java.time.LocalDateTime;

public class AlertaDTO {

    private Long id;
    private String descripcion;
    private String nivel;
    private LocalDateTime fecha;
    private String estado;
    private Long registroClimaId;
    private String ciudadNombre;

    public AlertaDTO() {}

    public AlertaDTO(Long id, String descripcion, String nivel, LocalDateTime fecha, String estado, Long registroClimaId, String ciudadNombre) {
        this.id = id;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.fecha = fecha;
        this.estado = estado;
        this.registroClimaId = registroClimaId;
        this.ciudadNombre = ciudadNombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getRegistroClimaId() {
        return registroClimaId;
    }

    public void setRegistroClimaId(Long registroClimaId) {
        this.registroClimaId = registroClimaId;
    }

    public String getCiudadNombre() {
        return ciudadNombre;
    }

    public void setCiudadNombre(String ciudadNombre) {
        this.ciudadNombre = ciudadNombre;
    }
}

