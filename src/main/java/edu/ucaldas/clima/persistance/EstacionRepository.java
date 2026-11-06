package edu.ucaldas.clima.persistance;

import edu.ucaldas.clima.model.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Long> {
    List<Estacion> findByCiudadIdOrderByIdAsc(Long ciudadId);
    Optional<Estacion> findByCodigo(String codigo);
}
