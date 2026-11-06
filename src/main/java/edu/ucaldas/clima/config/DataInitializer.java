package edu.ucaldas.clima.config;

import edu.ucaldas.clima.model.*;
import edu.ucaldas.clima.persistance.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CiudadRepository ciudadRepository;
    private final EstacionRepository estacionRepository;
    private final TipoMedicionRepository tipoMedicionRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final RegistroClimaRepository registroClimaRepository;
    private final AlertaRepository alertaRepository;

    public DataInitializer(CiudadRepository ciudadRepository,
                           EstacionRepository estacionRepository,
                           TipoMedicionRepository tipoMedicionRepository,
                           RolRepository rolRepository,
                           UsuarioRepository usuarioRepository,
                           RegistroClimaRepository registroClimaRepository,
                           AlertaRepository alertaRepository) {
        this.ciudadRepository = ciudadRepository;
        this.estacionRepository = estacionRepository;
        this.tipoMedicionRepository = tipoMedicionRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.registroClimaRepository = registroClimaRepository;
        this.alertaRepository = alertaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Solo sembrar datos iniciales si no existen ciudades en la base de datos
        if (ciudadRepository.count() > 0) {
            return;
        }

        // 1. Ciudades iniciales
        Ciudad manizales = new Ciudad("Manizales", "Caldas", 450000); // ID: 1
        Ciudad bogota = new Ciudad("Bogotá D.C.", "Cundinamarca", 7900000); // ID: 2
        ciudadRepository.save(manizales);
        ciudadRepository.save(bogota);

        // 2. Estaciones y Configuraciones (1:1 y 1:N)
        Estacion chipre = new Estacion("Estación Chipre", "EST-CHIPRE-01", "Mirador de Chipre", "ACTIVA", manizales); // ID: 1
        Configuracion configChipre = new Configuracion(30, 32.0);
        chipre.setConfiguracion(configChipre);
        estacionRepository.save(chipre);

        Estacion cable = new Estacion("Estación El Cable", "EST-CABLE-02", "Parque El Cable", "ACTIVA", manizales); // ID: 2
        Configuracion configCable = new Configuracion(60, 30.0);
        cable.setConfiguracion(configCable);
        estacionRepository.save(cable);

        // 3. Tipos de Medición (Catálogo)
        TipoMedicion temp = new TipoMedicion("Temperatura", "°C", "Medición de temperatura ambiental"); // ID: 1
        TipoMedicion hum = new TipoMedicion("Humedad Relativa", "%", "Porcentaje de humedad"); // ID: 2
        TipoMedicion pres = new TipoMedicion("Presión Atmosférica", "hPa", "Presión barométrica"); // ID: 3
        tipoMedicionRepository.save(temp);
        tipoMedicionRepository.save(hum);
        tipoMedicionRepository.save(pres);

        // 4. Roles y Usuarios (N:M)
        Rol rolAdmin = new Rol("ADMIN", "Administrador del sistema meteorológico"); // ID: 1
        Rol rolAnalista = new Rol("ANALISTA", "Analista de datos climáticos"); // ID: 2
        rolRepository.save(rolAdmin);
        rolRepository.save(rolAnalista);

        Usuario juan = new Usuario("Juan Pérez", "juan.perez@ucaldas.edu.co", "secret123"); // ID: 1
        juan.agregarRol(rolAdmin);
        usuarioRepository.save(juan);

        Usuario maria = new Usuario("María Gómez", "maria.gomez@ucaldas.edu.co", "segura456"); // ID: 2
        maria.agregarRol(rolAnalista);
        usuarioRepository.save(maria);

        // 5. Registros de Clima / Mediciones (1:N y N:M)
        RegistroClima medicion1 = new RegistroClima(32.5, 78, 1013.25, LocalDateTime.now().minusHours(2), "Día caluroso y soleado"); // ID: 1
        medicion1.setEstacion(chipre);
        medicion1.setTiposMedicion(Set.of(temp, hum, pres));
        registroClimaRepository.save(medicion1);

        RegistroClima medicion2 = new RegistroClima(21.0, 65, 1015.00, LocalDateTime.now().minusHours(1), "Clima templado normal"); // ID: 2
        medicion2.setEstacion(chipre);
        medicion2.setTiposMedicion(Set.of(temp, hum));
        registroClimaRepository.save(medicion2);

        // 6. Alertas (1:N con RegistroClima)
        Alerta alertaCalor = new Alerta("Alerta por ola de calor superior a 30°C", "ALTO", LocalDateTime.now(), "ACTIVA", medicion1); // ID: 1
        Alerta alertaLluvia = new Alerta("Probabilidad moderada de precipitaciones", "MEDIO", LocalDateTime.now().minusMinutes(30), "ACTIVA", medicion2); // ID: 2
        alertaRepository.save(alertaCalor);
        alertaRepository.save(alertaLluvia);
    }
}
