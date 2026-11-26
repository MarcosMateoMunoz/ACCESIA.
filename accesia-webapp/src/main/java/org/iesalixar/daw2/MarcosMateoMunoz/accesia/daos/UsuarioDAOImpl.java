package org.iesalixar.daw2.MarcosMateoMunoz.accesia.daos;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String SELECT =
            "SELECT id_usuario, email, nombre, apellidos, password, telefono, fecha_nacimiento, " +
            "idioma, activo, cuenta_confirmada, fecha_registro FROM usuario";

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id_usuario"));
        u.setEmail(rs.getString("email"));
        u.setNombre(rs.getString("nombre"));
        u.setApellidos(rs.getString("apellidos"));
        u.setPassword(rs.getString("password"));
        u.setTelefono(rs.getString("telefono"));
        Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        u.setFechaNacimiento(fechaNacimiento != null ? fechaNacimiento.toLocalDate() : null);
        u.setIdioma(rs.getString("idioma"));
        u.setActivo(rs.getBoolean("activo"));
        u.setCuentaConfirmada(rs.getBoolean("cuenta_confirmada"));
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        u.setFechaRegistro(fechaRegistro != null ? fechaRegistro.toLocalDateTime() : null);
        return u;
    };

    public UsuarioDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Usuario> findAllActivos() {
        return jdbcTemplate.query(SELECT + " WHERE activo = TRUE ORDER BY nombre, apellidos", rowMapper);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        List<Usuario> result = jdbcTemplate.query(SELECT + " WHERE id_usuario = ?", rowMapper, id);
        return result.stream().findFirst();
    }

    @Override
    public boolean existsActivoById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM usuario WHERE id_usuario = ? AND activo = TRUE", Integer.class, id);
        return count != null && count > 0;
    }
}
