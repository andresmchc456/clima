package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.domain.UsuarioService;
import edu.ucaldas.clima.dto.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Endpoint específico: Registrar usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO dto) {
        UsuarioDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Endpoint específico: Asignar rol al usuario
    @PostMapping("/{id}/roles/{rolId}")
    public ResponseEntity<UsuarioDTO> asignarRol(@PathVariable Long id, @PathVariable Long rolId) {
        return ResponseEntity.ok(service.asignarRol(id, rolId));
    }

    // Endpoint específico: Consultar usuarios por rol
    @GetMapping("/rol/{nombre}")
    public ResponseEntity<List<UsuarioDTO>> listarPorRol(@PathVariable String nombre) {
        return ResponseEntity.ok(service.listarPorRol(nombre));
    }
}

