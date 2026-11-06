package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.domain.CiudadService;
import edu.ucaldas.clima.dto.CiudadDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    private final CiudadService service;

    public CiudadController(CiudadService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CiudadDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CiudadDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CiudadDTO> crear(@RequestBody CiudadDTO dto) {
        CiudadDTO creada = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CiudadDTO> actualizar(@PathVariable Long id, @RequestBody CiudadDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

