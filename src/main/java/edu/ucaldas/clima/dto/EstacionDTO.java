package edu.ucaldas.clima.dto;

public class EstacionDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private String ubicacion;
    private String estado;
    private Long ciudadId;
    private String ciudadNombre;
    private ConfiguracionDTO configuracion;

    public EstacionDTO() {}

    public EstacionDTO(Long id, String nombre, String codigo, String ubicacion, String estado, Long ciudadId, String ciudadNombre, ConfiguracionDTO configuracion) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.ciudadId = ciudadId;
        this.ciudadNombre = ciudadNombre;
        this.configuracion = configuracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getCiudadNombre() {
        return ciudadNombre;
    }

    public void setCiudadNombre(String ciudadNombre) {
        this.ciudadNombre = ciudadNombre;
    }

    public ConfiguracionDTO getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionDTO configuracion) {
        this.configuracion = configuracion;
    }
}

