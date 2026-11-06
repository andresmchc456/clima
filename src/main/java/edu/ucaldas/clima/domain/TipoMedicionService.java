package edu.ucaldas.clima.domain;

import edu.ucaldas.clima.dto.TipoMedicionDTO;
import edu.ucaldas.clima.exceptions.ResourceNotFoundException;
import edu.ucaldas.clima.model.TipoMedicion;
import edu.ucaldas.clima.persistance.TipoMedicionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoMedicionService {

    private final TipoMedicionRepository repository;

    public TipoMedicionService(TipoMedicionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TipoMedicionDTO> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TipoMedicionDTO crear(TipoMedicionDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del tipo de medición es obligatorio.");
        }
        TipoMedicion tipo = new TipoMedicion(dto.getNombre(), dto.getUnidad(), dto.getDescripcion());
        return toDTO(repository.save(tipo));
    }

    public TipoMedicionDTO toDTO(TipoMedicion tipo) {
        return new TipoMedicionDTO(tipo.getId(), tipo.getNombre(), tipo.getUnidad(), tipo.getDescripcion());
    }
}

