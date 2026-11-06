package edu.ucaldas.clima.persistance;

import edu.ucaldas.clima.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE LOWER(r.nombre) = LOWER(:nombreRol) ORDER BY u.id ASC")
    List<Usuario> findByRolNombre(@Param("nombreRol") String nombreRol);
}
