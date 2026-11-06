package edu.ucaldas.clima.dto;

public class TipoMedicionDTO {
    private Long id;
    private String nombre;
    private String unidad;
    private String descripcion;

    public TipoMedicionDTO() {}

    public TipoMedicionDTO(Long id, String nombre, String unidad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.descripcion = descripcion;
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

