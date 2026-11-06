package edu.ucaldas.clima.dto;

public class CiudadDTO {
    private Long id;
    private String nombre;
    private String departamento;
    private Integer poblacion;

    public CiudadDTO() {}

    public CiudadDTO(Long id, String nombre, String departamento, Integer poblacion) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.poblacion = poblacion;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Integer getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Integer poblacion) {
        this.poblacion = poblacion;
    }
}

