package edu.ucaldas.clima.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(nullable = false, length = 30)
    private String nivel; // ej: BAJO, MEDIO, ALTO, CRITICO

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, length = 30)
    private String estado = "ACTIVA"; // ACTIVA, RESUELTA, INACTIVA

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_clima_id", nullable = false)
    @JsonIgnore
    private RegistroClima registroClima;

    public Alerta() {
        this.fecha = LocalDateTime.now();
    }

    public Alerta(Long id, String descripcion, String nivel, LocalDateTime fecha, String estado, RegistroClima registroClima) {
        this.id = id;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
        this.estado = estado != null ? estado : "ACTIVA";
        this.registroClima = registroClima;
    }

    public Alerta(String descripcion, String nivel, LocalDateTime fecha, String estado, RegistroClima registroClima) {
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
        this.estado = estado != null ? estado : "ACTIVA";
        this.registroClima = registroClima;
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

    public RegistroClima getRegistroClima() {
        return registroClima;
    }

    public void setRegistroClima(RegistroClima registroClima) {
        this.registroClima = registroClima;
    }
}

