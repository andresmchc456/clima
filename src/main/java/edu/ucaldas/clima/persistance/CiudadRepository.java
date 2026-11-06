package edu.ucaldas.clima.persistance;

import edu.ucaldas.clima.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    Optional<Ciudad> findByNombreIgnoreCase(String nombre);
}

