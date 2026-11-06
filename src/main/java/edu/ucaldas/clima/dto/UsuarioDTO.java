package edu.ucaldas.clima.dto;

import java.util.HashSet;
import java.util.Set;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private Set<String> roles = new HashSet<>();

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nombre, String correo, String contrasena, Set<String> roles) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.roles = roles != null ? roles : new HashSet<>();
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

