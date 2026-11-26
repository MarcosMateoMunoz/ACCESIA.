-- ============================================================
-- ACCESIA - Esquema (entidad relacionada: USUARIO, principal: ESTABLECIMIENTO)
-- Relación 1:N -> un USUARIO (propietario) gestiona N ESTABLECIMIENTOS
-- ============================================================

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario        BIGINT        NOT NULL AUTO_INCREMENT,
    email             VARCHAR(150)  NOT NULL,
    nombre            VARCHAR(60)   NOT NULL,
    apellidos         VARCHAR(100)  NOT NULL,
    password          VARCHAR(100)  NOT NULL,          -- hash BCrypt
    telefono          VARCHAR(20)   NOT NULL,
    fecha_nacimiento  DATE          NOT NULL,          -- la edad es un atributo derivado (se calcula)
    idioma            VARCHAR(2)    NOT NULL DEFAULT 'es',
    activo            BOOLEAN       NOT NULL DEFAULT TRUE,
    cuenta_confirmada BOOLEAN       NOT NULL DEFAULT FALSE,
    fecha_registro    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario),
    UNIQUE KEY uk_usuario_email (email),
    UNIQUE KEY uk_usuario_telefono (telefono)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS establecimiento (
    id_establecimiento      BIGINT        NOT NULL AUTO_INCREMENT,
    nombre                  VARCHAR(100)  NOT NULL,
    tipo                    VARCHAR(30)   NOT NULL,
    direccion               VARCHAR(200)  NOT NULL,
    descripcion             VARCHAR(500)  NULL,
    aforo_maximo            INT           NOT NULL,
    precio_referencia       DECIMAL(8,2)  NOT NULL DEFAULT 0.00,
    edad_minima             INT           NULL,
    exige_cuenta_confirmada BOOLEAN       NOT NULL DEFAULT FALSE,
    aforo_publico           BOOLEAN       NOT NULL DEFAULT TRUE,
    activo                  BOOLEAN       NOT NULL DEFAULT TRUE,
    imagen                  VARCHAR(255)  NULL,        -- nombre del fichero subido
    id_propietario          BIGINT        NOT NULL,    -- FK hacia USUARIO (lado N de la relación 1:N)
    PRIMARY KEY (id_establecimiento),
    UNIQUE KEY uk_establecimiento_nombre (nombre),
    CONSTRAINT fk_establecimiento_propietario
        FOREIGN KEY (id_propietario) REFERENCES usuario (id_usuario)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
