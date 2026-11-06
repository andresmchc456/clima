package edu.ucaldas.clima.dto;

public class ConfiguracionDTO {
    private Long id;
    private Integer frecuenciaTransmision;
    private Double rangoAlertaTemperatura;

    public ConfiguracionDTO() {}

    public ConfiguracionDTO(Long id, Integer frecuenciaTransmision, Double rangoAlertaTemperatura) {
        this.id = id;
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
}

