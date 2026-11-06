package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.domain.AlertaService;
import edu.ucaldas.clima.dto.AlertaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlertaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Endpoint específico: Crear alerta
    @PostMapping
    public ResponseEntity<AlertaDTO> crear(@RequestBody AlertaDTO dto) {
        AlertaDTO creada = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // Endpoint específico: Consultar alertas por nivel
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<AlertaDTO>> listarPorNivel(@PathVariable String nivel) {
        return ResponseEntity.ok(service.listarPorNivel(nivel));
    }

    // Reto Adicional 3: Consultar las alertas activas de una ciudad determinada
    @GetMapping("/activas/ciudad/{ciudadId}")
    public ResponseEntity<List<AlertaDTO>> listarAlertasActivasPorCiudad(@PathVariable Long ciudadId) {
        return ResponseEntity.ok(service.listarAlertasActivasPorCiudad(ciudadId));
    }
}

