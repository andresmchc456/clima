package edu.ucaldas.clima.persistance;

import edu.ucaldas.clima.model.RegistroClima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroClimaRepository extends JpaRepository<RegistroClima, Long> {

    List<RegistroClima> findByEstacionIdOrderByIdAsc(Long estacionId);

    // Reto Adicional 1: Obtener mediciones cuya temperatura sea superior a un valor ordenadas por id
    @Query("SELECT r FROM RegistroClima r WHERE r.temperatura > :temperatura ORDER BY r.id ASC")
    List<RegistroClima> findByTemperaturaMayorA(@Param("temperatura") Double temperatura);

    // Reto Adicional 2: Consultar el promedio de temperatura de una ciudad
    @Query("""
        SELECT AVG(r.temperatura)
        FROM RegistroClima r
        WHERE r.estacion.ciudad.id = :ciudadId
    """)
    Double obtenerTemperaturaPromedio(@Param("ciudadId") Long ciudadId);
}
