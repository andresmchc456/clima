package edu.ucaldas.clima.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "configuraciones")
public class Configuracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "frecuencia_transmision")
    private Integer frecuenciaTransmision; // en minutos

    @Column(name = "rango_alerta_temperatura")
    private Double rangoAlertaTemperatura;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacion_id", unique = true)
    @JsonIgnore
    private Estacion estacion;

    public Configuracion() {
    }

    public Configuracion(Long id, Integer frecuenciaTransmision, Double rangoAlertaTemperatura) {
        this.id = id;
        this.frecuenciaTransmision = frecuenciaTransmision;
        this.rangoAlertaTemperatura = rangoAlertaTemperatura;
    }

    public Configuracion(Integer frecuenciaTransmision, Double rangoAlertaTemperatura) {
        this.frecuenciaTransmision = frecuenciaTransmision;
        this.rangoAlertaTemperatura = rangoAlertaTemperatura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFrecuenciaTransmision() {
        return frecuenciaTransmision;
    }

    public void setFrecuenciaTransmision(Integer frecuenciaTransmision) {
        this.frecuenciaTransmision = frecuenciaTransmision;
    }

    public Double getRangoAlertaTemperatura() {
        return rangoAlertaTemperatura;
    }

    public void setRangoAlertaTemperatura(Double rangoAlertaTemperatura) {
        this.rangoAlertaTemperatura = rangoAlertaTemperatura;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }
}

