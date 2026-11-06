package edu.ucaldas.clima.domain;

import edu.ucaldas.clima.dto.RolDTO;
import edu.ucaldas.clima.exceptions.ResourceNotFoundException;
import edu.ucaldas.clima.model.Rol;
import edu.ucaldas.clima.persistance.RolRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolService {

    private final RolRepository repository;

    public RolService(RolRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<RolDTO> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RolDTO crear(RolDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio.");
        }
        Rol rol = new Rol(dto.getNombre().toUpperCase(), dto.getDescripcion());
        return toDTO(repository.save(rol));
    }

    public RolDTO toDTO(Rol rol) {
        return new RolDTO(rol.getId(), rol.getNombre(), rol.getDescripcion());
    }
}

