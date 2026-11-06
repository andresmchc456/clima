package edu.ucaldas.clima.domain;

import edu.ucaldas.clima.dto.ConfiguracionDTO;
import edu.ucaldas.clima.dto.EstacionDTO;
import edu.ucaldas.clima.exceptions.ResourceNotFoundException;
import edu.ucaldas.clima.model.Ciudad;
import edu.ucaldas.clima.model.Configuracion;
import edu.ucaldas.clima.model.Estacion;
import edu.ucaldas.clima.persistance.CiudadRepository;
import edu.ucaldas.clima.persistance.EstacionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionService {

    private final EstacionRepository estacionRepository;
    private final CiudadRepository ciudadRepository;

    public EstacionService(EstacionRepository estacionRepository, CiudadRepository ciudadRepository) {
        this.estacionRepository = estacionRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional(readOnly = true)
    public List<EstacionDTO> listarTodas() {
        return estacionRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstacionDTO buscarPorId(Long id) {
        Estacion estacion = estacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estación no encontrada con id: " + id));
        return toDTO(estacion);
    }

    @Transactional(readOnly = true)
    public List<EstacionDTO> listarPorCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {
            throw new ResourceNotFoundException("Ciudad no encontrada con id: " + ciudadId);
        }
        return estacionRepository.findByCiudadIdOrderByIdAsc(ciudadId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EstacionDTO crear(EstacionDTO dto) {
        if (dto.getCiudadId() == null) {
            throw new IllegalArgumentException("El id de la ciudad es obligatorio para asociar la estación.");
        }
        Ciudad ciudad = ciudadRepository.findById(dto.getCiudadId())
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con id: " + dto.getCiudadId()));

        Estacion estacion = new Estacion();
        estacion.setNombre(dto.getNombre());
        estacion.setCodigo(dto.getCodigo());
        estacion.setUbicacion(dto.getUbicacion());
        estacion.setEstado(dto.getEstado() != null ? dto.getEstado() : "ACTIVA");
        estacion.setCiudad(ciudad);

        if (dto.getConfiguracion() != null) {
            Configuracion config = new Configuracion();
            config.setFrecuenciaTransmision(dto.getConfiguracion().getFrecuenciaTransmision());
            config.setRangoAlertaTemperatura(dto.getConfiguracion().getRangoAlertaTemperatura());
            estacion.setConfiguracion(config);
        }

        Estacion guardada = estacionRepository.save(estacion);
        return toDTO(guardada);
    }

    @Transactional
    public EstacionDTO actualizar(Long id, EstacionDTO dto) {
        Estacion estacion = estacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estación no encontrada con id: " + id));

        if (dto.getCiudadId() != null && !dto.getCiudadId().equals(estacion.getCiudad().getId())) {
            Ciudad nuevaCiudad = ciudadRepository.findById(dto.getCiudadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con id: " + dto.getCiudadId()));
            estacion.setCiudad(nuevaCiudad);
        }

        estacion.setNombre(dto.getNombre());
        estacion.setCodigo(dto.getCodigo());
        estacion.setUbicacion(dto.getUbicacion());
        if (dto.getEstado() != null) {
            estacion.setEstado(dto.getEstado());
        }

        if (dto.getConfiguracion() != null) {
            if (estacion.getConfiguracion() == null) {
                Configuracion config = new Configuracion();
                config.setFrecuenciaTransmision(dto.getConfiguracion().getFrecuenciaTransmision());
                config.setRangoAlertaTemperatura(dto.getConfiguracion().getRangoAlertaTemperatura());
                estacion.setConfiguracion(config);
            } else {
                estacion.getConfiguracion().setFrecuenciaTransmision(dto.getConfiguracion().getFrecuenciaTransmision());
                estacion.getConfiguracion().setRangoAlertaTemperatura(dto.getConfiguracion().getRangoAlertaTemperatura());
            }
        }

        return toDTO(estacionRepository.save(estacion));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!estacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estación no encontrada con id: " + id);
        }
        estacionRepository.deleteById(id);
    }

    public EstacionDTO toDTO(Estacion estacion) {
        ConfiguracionDTO configDTO = null;
        if (estacion.getConfiguracion() != null) {
            configDTO = new ConfiguracionDTO(
                    estacion.getConfiguracion().getId(),
                    estacion.getConfiguracion().getFrecuenciaTransmision(),
                    estacion.getConfiguracion().getRangoAlertaTemperatura()
            );
        }

        return new EstacionDTO(
                estacion.getId(),
                estacion.getNombre(),
                estacion.getCodigo(),
                estacion.getUbicacion(),
                estacion.getEstado(),
                estacion.getCiudad() != null ? estacion.getCiudad().getId() : null,
                estacion.getCiudad() != null ? estacion.getCiudad().getNombre() : null,
                configDTO
        );
    }
}

