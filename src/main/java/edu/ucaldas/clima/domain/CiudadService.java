package edu.ucaldas.clima.domain;

import edu.ucaldas.clima.dto.CiudadDTO;
import edu.ucaldas.clima.exceptions.ResourceNotFoundException;
import edu.ucaldas.clima.model.Ciudad;
import edu.ucaldas.clima.persistance.CiudadRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CiudadService {

    private final CiudadRepository repository;

    public CiudadService(CiudadRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CiudadDTO> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CiudadDTO buscarPorId(Long id) {
        Ciudad ciudad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con id: " + id));
        return toDTO(ciudad);
    }

    @Transactional
    public CiudadDTO crear(CiudadDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la ciudad es obligatorio.");
        }
        Ciudad ciudad = new Ciudad(dto.getNombre(), dto.getDepartamento(), dto.getPoblacion());
        Ciudad guardada = repository.save(ciudad);
        return toDTO(guardada);
    }

    @Transactional
    public CiudadDTO actualizar(Long id, CiudadDTO dto) {
        Ciudad ciudad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con id: " + id));

        ciudad.setNombre(dto.getNombre());
        ciudad.setDepartamento(dto.getDepartamento());
        ciudad.setPoblacion(dto.getPoblacion());

        return toDTO(repository.save(ciudad));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ciudad no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    public CiudadDTO toDTO(Ciudad ciudad) {
        return new CiudadDTO(
                ciudad.getId(),
                ciudad.getNombre(),
                ciudad.getDepartamento(),
                ciudad.getPoblacion()
        );
    }
}

