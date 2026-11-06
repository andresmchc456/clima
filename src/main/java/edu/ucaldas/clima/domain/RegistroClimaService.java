package edu.ucaldas.clima.domain;

import edu.ucaldas.clima.dto.RegistroClimaDTO;
import edu.ucaldas.clima.exceptions.ResourceNotFoundException;
import edu.ucaldas.clima.model.Estacion;
import edu.ucaldas.clima.model.RegistroClima;
import edu.ucaldas.clima.model.TipoMedicion;
import edu.ucaldas.clima.persistance.CiudadRepository;
import edu.ucaldas.clima.persistance.EstacionRepository;
import edu.ucaldas.clima.persistance.RegistroClimaRepository;
import edu.ucaldas.clima.persistance.TipoMedicionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegistroClimaService {

    private final RegistroClimaRepository repository;
    private final EstacionRepository estacionRepository;
    private final TipoMedicionRepository tipoMedicionRepository;
    private final CiudadRepository ciudadRepository;

    public RegistroClimaService(RegistroClimaRepository repository,
                                EstacionRepository estacionRepository,
                                TipoMedicionRepository tipoMedicionRepository,
                                CiudadRepository ciudadRepository) {
        this.repository = repository;
        this.estacionRepository = estacionRepository;
        this.tipoMedicionRepository = tipoMedicionRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional(readOnly = true)
    public List<RegistroClimaDTO> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RegistroClimaDTO buscarPorId(Long id) {
        RegistroClima registro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de clima no encontrado con id: " + id));
        return toDTO(registro);
    }

    @Transactional(readOnly = true)
    public List<RegistroClimaDTO> listarPorEstacion(Long estacionId) {
        if (!estacionRepository.existsById(estacionId)) {
            throw new ResourceNotFoundException("Estación no encontrada con id: " + estacionId);
        }
        return repository.findByEstacionIdOrderByIdAsc(estacionId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RegistroClimaDTO crear(RegistroClimaDTO dto) {
        if (dto.getEstacionId() == null) {
            throw new IllegalArgumentException("El id de la estación es obligatorio.");
        }
        Estacion estacion = estacionRepository.findById(dto.getEstacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Estación no encontrada con id: " + dto.getEstacionId()));

        RegistroClima registro = new RegistroClima();
        registro.setTemperatura(dto.getTemperatura());
        registro.setHumedad(dto.getHumedad());
        registro.setPresion(dto.getPresion());
        registro.setDescripcion(dto.getDescripcion());
        registro.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());
        registro.setEstacion(estacion);

        if (dto.getTipoMedicionIds() != null && !dto.getTipoMedicionIds().isEmpty()) {
            Set<TipoMedicion> tipos = new HashSet<>(tipoMedicionRepository.findAllById(dto.getTipoMedicionIds()));
            registro.setTiposMedicion(tipos);
        }

        RegistroClima guardado = repository.save(registro);
        return toDTO(guardado);
    }

    @Transactional
    public RegistroClimaDTO actualizar(Long id, RegistroClimaDTO dto) {
        RegistroClima registro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de clima no encontrado con id: " + id));

        if (dto.getEstacionId() != null && (registro.getEstacion() == null || !dto.getEstacionId().equals(registro.getEstacion().getId()))) {
            Estacion estacion = estacionRepository.findById(dto.getEstacionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estación no encontrada con id: " + dto.getEstacionId()));
            registro.setEstacion(estacion);
        }

        registro.setTemperatura(dto.getTemperatura());
        registro.setHumedad(dto.getHumedad());
        registro.setPresion(dto.getPresion());
        registro.setDescripcion(dto.getDescripcion());
        if (dto.getFecha() != null) {
            registro.setFecha(dto.getFecha());
        }

        if (dto.getTipoMedicionIds() != null) {
            Set<TipoMedicion> tipos = new HashSet<>(tipoMedicionRepository.findAllById(dto.getTipoMedicionIds()));
            registro.setTiposMedicion(tipos);
        }

        return toDTO(repository.save(registro));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de clima no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    // Reto Adicional 1: Mediciones con temperatura > 30 °C
    @Transactional(readOnly = true)
    public List<RegistroClimaDTO> obtenerConTemperaturaMayorA(Double temp) {
        Double limite = temp != null ? temp : 30.0;
        return repository.findByTemperaturaMayorA(limite).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Reto Adicional 2: Promedio de temperatura de una ciudad
    @Transactional(readOnly = true)
    public Double obtenerTemperaturaPromedioPorCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {
            throw new ResourceNotFoundException("Ciudad no encontrada con id: " + ciudadId);
        }
        Double promedio = repository.obtenerTemperaturaPromedio(ciudadId);
        if (promedio != null) {
            return promedio;
        } else {
            return 0.0;
        }
    }

    public RegistroClimaDTO toDTO(RegistroClima registro) {
        RegistroClimaDTO dto = new RegistroClimaDTO(
                registro.getId(),
                registro.getTemperatura(),
                registro.getHumedad(),
                registro.getPresion(),
                registro.getFecha(),
                registro.getDescripcion(),
                registro.getEstacion() != null ? registro.getEstacion().getId() : null,
                registro.getEstacion() != null ? registro.getEstacion().getCodigo() : null
        );

        if (registro.getTiposMedicion() != null) {
            dto.setTipoMedicionIds(registro.getTiposMedicion().stream().map(TipoMedicion::getId).collect(Collectors.toSet()));
            dto.setTiposMedicionNombres(registro.getTiposMedicion().stream().map(TipoMedicion::getNombre).collect(Collectors.toSet()));
        }

        return dto;
    }
}
