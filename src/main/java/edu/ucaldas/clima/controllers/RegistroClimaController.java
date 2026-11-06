package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.domain.RegistroClimaService;
import edu.ucaldas.clima.dto.RegistroClimaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mediciones")
public class RegistroClimaController {

    private final RegistroClimaService service;

    public RegistroClimaController(RegistroClimaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RegistroClimaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroClimaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Endpoint específico: Registrar medición de una estación
    @PostMapping
    public ResponseEntity<RegistroClimaDTO> crear(@RequestBody RegistroClimaDTO dto) {
        RegistroClimaDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Endpoint específico: Consultar mediciones de una estación
    @GetMapping("/estacion/{id}")
    public ResponseEntity<List<RegistroClimaDTO>> listarPorEstacion(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorEstacion(id));
    }

    // Reto Adicional 1: Obtener mediciones cuya temperatura sea superior a 30 °C
    @GetMapping("/temperatura-mayor")
    public ResponseEntity<List<RegistroClimaDTO>> obtenerPorTemperaturaMayor(
            @RequestParam(name = "min", defaultValue = "30.0") Double min) {
        return ResponseEntity.ok(service.obtenerConTemperaturaMayorA(min));
    }

    // Reto Adicional 2: Consultar el promedio de temperatura de una ciudad
    @GetMapping("/promedio-ciudad/{ciudadId}")
    public ResponseEntity<Map<String, Object>> obtenerTemperaturaPromedioPorCiudad(@PathVariable Long ciudadId) {
        Double promedio = service.obtenerTemperaturaPromedioPorCiudad(ciudadId);
        return ResponseEntity.ok(Map.of(
                "ciudadId", ciudadId,
                "temperaturaPromedio", promedio
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroClimaDTO> actualizar(@PathVariable Long id, @RequestBody RegistroClimaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
