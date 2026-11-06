package edu.ucaldas.clima.domain;

import edu.ucaldas.clima.dto.AlertaDTO;
import edu.ucaldas.clima.exceptions.ResourceNotFoundException;
import edu.ucaldas.clima.model.Alerta;
import edu.ucaldas.clima.model.RegistroClima;
import edu.ucaldas.clima.persistance.AlertaRepository;
import edu.ucaldas.clima.persistance.CiudadRepository;
import edu.ucaldas.clima.persistance.RegistroClimaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final RegistroClimaRepository registroClimaRepository;
    private final CiudadRepository ciudadRepository;

    public AlertaService(AlertaRepository alertaRepository,
                         RegistroClimaRepository registroClimaRepository,
                         CiudadRepository ciudadRepository) {
        this.alertaRepository = alertaRepository;
        this.registroClimaRepository = registroClimaRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional(readOnly = true)
    public List<AlertaDTO> listar() {
        return alertaRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlertaDTO buscarPorId(Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con id: " + id));
        return toDTO(alerta);
    }

    @Transactional
    public AlertaDTO crear(AlertaDTO dto) {
        if (dto.getRegistroClimaId() == null) {
            throw new IllegalArgumentException("El id del registro de clima es obligatorio para crear una alerta.");
        }
        RegistroClima registro = registroClimaRepository.findById(dto.getRegistroClimaId())
                .orElseThrow(() -> new ResourceNotFoundException("Registro de clima no encontrado con id: " + dto.getRegistroClimaId()));

        Alerta alerta = new Alerta();
        alerta.setDescripcion(dto.getDescripcion());
        alerta.setNivel(dto.getNivel() != null ? dto.getNivel().toUpperCase() : "MEDIO");
        alerta.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());
        alerta.setEstado(dto.getEstado() != null ? dto.getEstado().toUpperCase() : "ACTIVA");
        alerta.setRegistroClima(registro);

        Alerta guardada = alertaRepository.save(alerta);
        return toDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<AlertaDTO> listarPorNivel(String nivel) {
        return alertaRepository.findByNivelIgnoreCaseOrderByIdAsc(nivel).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Reto Adicional 3: Consultar alertas activas de una ciudad determinada
    @Transactional(readOnly = true)
    public List<AlertaDTO> listarAlertasActivasPorCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {
            throw new ResourceNotFoundException("Ciudad no encontrada con id: " + ciudadId);
        }
        return alertaRepository.findAlertasActivasPorCiudad(ciudadId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AlertaDTO toDTO(Alerta alerta) {
        String ciudadNombre = null;
        if (alerta.getRegistroClima() != null
                && alerta.getRegistroClima().getEstacion() != null
                && alerta.getRegistroClima().getEstacion().getCiudad() != null) {
            ciudadNombre = alerta.getRegistroClima().getEstacion().getCiudad().getNombre();
        }

        return new AlertaDTO(
                alerta.getId(),
                alerta.getDescripcion(),
                alerta.getNivel(),
                alerta.getFecha(),
                alerta.getEstado(),
                alerta.getRegistroClima() != null ? alerta.getRegistroClima().getId() : null,
                ciudadNombre
        );
    }
}

