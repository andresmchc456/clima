package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.domain.EstacionService;
import edu.ucaldas.clima.dto.EstacionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionController {

    private final EstacionService service;

    public EstacionController(EstacionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EstacionDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Endpoint específico: Registrar estación asociada a ciudad
    @PostMapping
    public ResponseEntity<EstacionDTO> crear(@RequestBody EstacionDTO dto) {
        EstacionDTO creada = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // Endpoint específico: Consultar estaciones de una ciudad
    @GetMapping("/ciudad/{id}")
    public ResponseEntity<List<EstacionDTO>> listarPorCiudad(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorCiudad(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstacionDTO> actualizar(@PathVariable Long id, @RequestBody EstacionDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

