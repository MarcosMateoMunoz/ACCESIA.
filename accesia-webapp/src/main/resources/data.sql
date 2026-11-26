-- ============================================================
-- Datos de ejemplo (INSERT IGNORE: se puede ejecutar en cada arranque sin duplicar)
-- El hash de password es un hash BCrypt de ejemplo (no se usa login en este CRUD).
-- ============================================================

INSERT IGNORE INTO usuario
    (id_usuario, email, nombre, apellidos, password, telefono, fecha_nacimiento, idioma, activo, cuenta_confirmada)
VALUES
    (1, 'lucia.garcia@accesia.es',  'Lucía',  'García Pérez',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '+34600111222', '1988-03-14', 'es', TRUE,  TRUE),
    (2, 'carlos.ruiz@accesia.es',   'Carlos', 'Ruiz Martín',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '+34600333444', '1979-11-02', 'es', TRUE,  TRUE),
    (3, 'emma.smith@accesia.es',    'Emma',   'Smith Johnson',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '+34600555666', '1992-07-25', 'en', TRUE,  TRUE),
    (4, 'pablo.lopez@accesia.es',   'Pablo',  'López Sánchez',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '+34600777888', '1995-01-30', 'es', TRUE,  FALSE);

INSERT IGNORE INTO establecimiento
    (id_establecimiento, nombre, tipo, direccion, descripcion, aforo_maximo, precio_referencia, edad_minima, exige_cuenta_confirmada, aforo_publico, activo, imagen, id_propietario)
VALUES
    (1, 'Sala Luna Nueva',       'DISCOTECA',         'Calle Betis 24, Sevilla',              'Sala de música electrónica con dos ambientes.',          600, 15.00, 18, TRUE,  TRUE,  TRUE, NULL, 1),
    (2, 'Teatro Maestranza',     'CONCIERTO',         'Paseo de Cristóbal Colón 22, Sevilla', 'Conciertos de música clásica y ópera.',                  1800, 45.00, NULL, FALSE, TRUE,  TRUE, NULL, 2),
    (3, 'Gimnasio FitCenter',    'GIMNASIO',          'Avenida de la Ciencia 5, Mairena',     'Gimnasio con sala de musculación y clases dirigidas.',   250,  30.00, 16, TRUE,  FALSE, TRUE, NULL, 3),
    (4, 'Museo del Aljarafe',    'MUSEO',             'Plaza del Cabildo 1, Mairena',         'Colección permanente de historia local.',                120,   5.00, NULL, FALSE, TRUE,  TRUE, NULL, 2),
    (5, 'Estadio Ciudad Verde',  'EVENTO_DEPORTIVO',  'Avenida del Deporte s/n, Sevilla',     'Estadio para partidos y eventos deportivos.',            8000, 25.00, NULL, FALSE, TRUE,  TRUE, NULL, 1),
    (6, 'Club Noches de Triana', 'DISCOTECA',         'Calle Pureza 12, Sevilla',             'Club con música latina en directo.',                     400,  12.00, 18, TRUE,  TRUE,  FALSE, NULL, 4),
    (7, 'Auditorio Rocío Jurado','CONCIERTO',         'Isla de la Cartuja, Sevilla',          'Auditorio al aire libre para grandes conciertos.',       3000, 55.00, NULL, FALSE, TRUE,  TRUE, NULL, 3),
    (8, 'Sala Escape Room X',    'OTRO',              'Calle Sierpes 40, Sevilla',            NULL,                                                      40,  18.50, 12, FALSE, FALSE, TRUE, NULL, 4);
