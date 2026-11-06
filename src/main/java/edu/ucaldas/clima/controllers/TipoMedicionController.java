package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.domain.TipoMedicionService;
import edu.ucaldas.clima.dto.TipoMedicionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-medicion")
public class TipoMedicionController {

    private final TipoMedicionService service;

    public TipoMedicionController(TipoMedicionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoMedicionDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<TipoMedicionDTO> crear(@RequestBody TipoMedicionDTO dto) {
        TipoMedicionDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}

