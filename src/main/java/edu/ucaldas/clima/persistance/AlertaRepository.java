package edu.ucaldas.clima.persistance;

import edu.ucaldas.clima.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByNivelIgnoreCaseOrderByIdAsc(String nivel);

    List<Alerta> findByEstadoIgnoreCaseOrderByIdAsc(String estado);

    // Reto Adicional 3: Consultar las alertas activas de una ciudad determinada
    @Query("""
        SELECT a FROM Alerta a
        WHERE a.registroClima.estacion.ciudad.id = :ciudadId
        AND UPPER(a.estado) = 'ACTIVA'
        ORDER BY a.id ASC
    """)
    List<Alerta> findAlertasActivasPorCiudad(@Param("ciudadId") Long ciudadId);
}
