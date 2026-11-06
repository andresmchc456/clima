package edu.ucaldas.clima.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class RegistroClimaDTO {

    private Long id;
    private Double temperatura;
    private Integer humedad;
    private Double presion;
    private LocalDateTime fecha;
    private String descripcion;
    private Long estacionId;
    private String estacionCodigo;
    private Set<Long> tipoMedicionIds = new HashSet<>();
    private Set<String> tiposMedicionNombres = new HashSet<>();

    public RegistroClimaDTO() {}

    public RegistroClimaDTO(Long id, Double temperatura, Integer humedad, Double presion, LocalDateTime fecha, String descripcion, Long estacionId, String estacionCodigo) {
        this.id = id;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.presion = presion;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estacionId = estacionId;
        this.estacionCodigo = estacionCodigo;
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

    public Long getEstacionId() {
        return estacionId;
    }

    public void setEstacionId(Long estacionId) {
        this.estacionId = estacionId;
    }

    public String getEstacionCodigo() {
        return estacionCodigo;
    }

    public void setEstacionCodigo(String estacionCodigo) {
        this.estacionCodigo = estacionCodigo;
    }

    public Set<Long> getTipoMedicionIds() {
        return tipoMedicionIds;
    }

    public void setTipoMedicionIds(Set<Long> tipoMedicionIds) {
        this.tipoMedicionIds = tipoMedicionIds;
    }

    public Set<String> getTiposMedicionNombres() {
        return tiposMedicionNombres;
    }

    public void setTiposMedicionNombres(Set<String> tiposMedicionNombres) {
        this.tiposMedicionNombres = tiposMedicionNombres;
    }
}
