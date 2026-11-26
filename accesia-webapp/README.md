# ACCESIA - Tu acceso, bajo control

CRUD de **Establecimiento** con relación **1:N** hacia **Usuario** (un usuario es propietario de varios establecimientos).
Spring Boot 3 + Thymeleaf + Spring JDBC (JdbcTemplate) + MariaDB.

## Cómo ejecutarlo en IntelliJ

1. Levantar la base de datos (requiere Docker):
   ```
   docker compose up -d
   ```
   Crea MariaDB en `localhost:3306` con la base `ticket-logger-webapp` y el usuario `myuser` / `mypassword` (valores del `.env`).
   Si ya tienes MariaDB local, crea esa misma base y usuario.
2. Abrir la carpeta como proyecto Maven en IntelliJ y esperar a que descargue dependencias.
3. Ejecutar `AccesiaWebappApplication`.
   - `schema.sql` y `data.sql` se ejecutan solos al arrancar (crean tablas y datos de ejemplo).
   - Si no defines variables de entorno, se usan los mismos valores del `.env` por defecto.
4. Abrir http://localhost:8080 (cambio de idioma: `?lang=es` / `?lang=en`, o botones ES/EN de la cabecera).

## Qué probar en el vídeo

- Listado con el propietario de cada establecimiento (relación 1:N).
- Detalle con los datos del propietario.
- Alta / edición con `<select>` de propietario.
- Validaciones: campos vacíos, aforo 0, precio negativo, nombre duplicado, edad mínima >= 18 sin "cuenta confirmada", imagen > 2 MB o formato no válido.
- Subida de imagen (se guarda en `uploads/` y se muestra en listado y detalle), cambio de imagen y borrado.
- Cambio de idioma ES/EN.
