package edu.ucaldas.clima.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "registros_clima")
public class RegistroClima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double temperatura;

    private Integer humedad;

    private Double presion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(length = 255)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacion_id")
    @JsonIgnore
    private Estacion estacion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "registro_tipo_medicion",
        joinColumns = @JoinColumn(name = "registro_clima_id"),
        inverseJoinColumns = @JoinColumn(name = "tipo_medicion_id")
    )
    private Set<TipoMedicion> tiposMedicion = new HashSet<>();

    @OneToMany(mappedBy = "registroClima", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Alerta> alertas = new ArrayList<>();

    public RegistroClima() {
        this.fecha = LocalDateTime.now();
    }

    public RegistroClima(Double temperatura, Integer humedad, Double presion, LocalDateTime fecha, String descripcion) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.presion = presion;
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
        this.descripcion = descripcion;
    }

    public RegistroClima(Double temperatura, Integer humedad, String descripcion) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getHumedad() {
        return humedad;
    }

    public void setHumedad(Integer humedad) {
        this.humedad = humedad;
    }

    public Double getPresion() {
        return presion;
    }

    public void setPresion(Double presion) {
        this.presion = presion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public Set<TipoMedicion> getTiposMedicion() {
        return tiposMedicion;
    }

    public void setTiposMedicion(Set<TipoMedicion> tiposMedicion) {
        this.tiposMedicion = tiposMedicion;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }
}
