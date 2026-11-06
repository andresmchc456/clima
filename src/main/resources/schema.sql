-- =============================================================================
-- SCRIPT DDL: SISTEMA DE GESTIÓN METEOROLÓGICA (POSTGRESQL / SQL ESTÁNDAR)
-- Fase 1: Tablas, llaves primarias, llaves foráneas y restricciones
-- =============================================================================

-- 1. Tabla Ciudades
CREATE TABLE IF NOT EXISTS ciudades (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    poblacion INTEGER CHECK (poblacion >= 0)
);

-- 2. Tabla Estaciones
CREATE TABLE IF NOT EXISTS estaciones (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    ubicacion VARCHAR(255),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    ciudad_id BIGINT NOT NULL,
    CONSTRAINT fk_estacion_ciudad FOREIGN KEY (ciudad_id) REFERENCES ciudades (id) ON DELETE CASCADE
);

-- 3. Tabla Configuracion (Relación 1:1 con Estaciones)
CREATE TABLE IF NOT EXISTS configuraciones (
    id BIGSERIAL PRIMARY KEY,
    frecuencia_transmision INTEGER NOT NULL DEFAULT 60, -- en minutos
    rango_alerta_temperatura NUMERIC(5, 2) DEFAULT 35.00,
    estacion_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_configuracion_estacion FOREIGN KEY (estacion_id) REFERENCES estaciones (id) ON DELETE CASCADE
);

-- 4. Tabla Tipos de Medición (Catálogo de variables)
CREATE TABLE IF NOT EXISTS tipos_medicion (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    unidad VARCHAR(20) NOT NULL,
    descripcion VARCHAR(255)
);

-- 5. Tabla Registros de Clima (Mediciones)
CREATE TABLE IF NOT EXISTS registros_clima (
    id BIGSERIAL PRIMARY KEY,
    temperatura NUMERIC(5, 2) NOT NULL,
    humedad INTEGER CHECK (humedad BETWEEN 0 AND 100),
    presion NUMERIC(7, 2),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descripcion VARCHAR(255),
    estacion_id BIGINT NOT NULL,
    CONSTRAINT fk_registro_estacion FOREIGN KEY (estacion_id) REFERENCES estaciones (id) ON DELETE CASCADE
);

-- 6. Tabla Intermedia: RegistroClima - TipoMedicion (Relación N:M)
CREATE TABLE IF NOT EXISTS registro_tipo_medicion (
    registro_clima_id BIGINT NOT NULL,
    tipo_medicion_id BIGINT NOT NULL,
    PRIMARY KEY (registro_clima_id, tipo_medicion_id),
    CONSTRAINT fk_rtm_registro FOREIGN KEY (registro_clima_id) REFERENCES registros_clima (id) ON DELETE CASCADE,
    CONSTRAINT fk_rtm_tipo FOREIGN KEY (tipo_medicion_id) REFERENCES tipos_medicion (id) ON DELETE CASCADE
);

-- 7. Tabla Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL
);

-- 8. Tabla Roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- 9. Tabla Intermedia: Usuario - Rol (Relación N:M)
CREATE TABLE IF NOT EXISTS usuario_rol (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    CONSTRAINT fk_ur_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE,
    CONSTRAINT fk_ur_rol FOREIGN KEY (rol_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- 10. Tabla Alertas (Relación 1:N con RegistroClima)
CREATE TABLE IF NOT EXISTS alertas (
    id BIGSERIAL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    nivel VARCHAR(30) NOT NULL, -- ej: BAJO, MEDIO, ALTO, CRITICO
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(30) NOT NULL DEFAULT 'ACTIVA', -- ej: ACTIVA, RESUELTA, INACTIVA
    registro_clima_id BIGINT NOT NULL,
    CONSTRAINT fk_alerta_registro FOREIGN KEY (registro_clima_id) REFERENCES registros_clima (id) ON DELETE CASCADE
);

-- Índices recomendados para optimización de consultas frecuentes
CREATE INDEX IF NOT EXISTS idx_estacion_ciudad ON estaciones (ciudad_id);
CREATE INDEX IF NOT EXISTS idx_registro_estacion ON registros_clima (estacion_id);
CREATE INDEX IF NOT EXISTS idx_registro_temperatura ON registros_clima (temperatura);
CREATE INDEX IF NOT EXISTS idx_alerta_nivel ON alertas (nivel);
CREATE INDEX IF NOT EXISTS idx_alerta_estado ON alertas (estado);

