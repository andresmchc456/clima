package edu.ucaldas.clima.domain;

import edu.ucaldas.clima.dto.UsuarioDTO;
import edu.ucaldas.clima.exceptions.ResourceNotFoundException;
import edu.ucaldas.clima.model.Rol;
import edu.ucaldas.clima.model.Usuario;
import edu.ucaldas.clima.persistance.RolRepository;
import edu.ucaldas.clima.persistance.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return toDTO(usuario);
    }

    @Transactional
    public UsuarioDTO crear(UsuarioDTO dto) {
        if (dto.getCorreo() == null || dto.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El correo del usuario es obligatorio.");
        }
        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con el correo: " + dto.getCorreo());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContrasena());

        Usuario guardado = usuarioRepository.save(usuario);
        return toDTO(guardado);
    }

    @Transactional
    public UsuarioDTO asignarRol(Long usuarioId, Long rolId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + rolId));

        usuario.agregarRol(rol);
        return toDTO(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarPorRol(String nombreRol) {
        return usuarioRepository.findByRolNombre(nombreRol).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        // No retornamos la contraseña por seguridad
        dto.setContrasena(null);
        if (usuario.getRoles() != null) {
            dto.setRoles(usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet()));
        }
        return dto;
    }
}

