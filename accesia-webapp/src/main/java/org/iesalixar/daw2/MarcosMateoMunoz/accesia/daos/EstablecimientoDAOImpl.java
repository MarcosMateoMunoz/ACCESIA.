package org.iesalixar.daw2.MarcosMateoMunoz.accesia.daos;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Establecimiento;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class EstablecimientoDAOImpl implements EstablecimientoDAO {

    /** JOIN con USUARIO para traer también los datos del propietario (relación N:1). */
    private static final String SELECT =
            "SELECT e.id_establecimiento, e.nombre, e.tipo, e.direccion, e.descripcion, e.aforo_maximo, " +
            "e.precio_referencia, e.edad_minima, e.exige_cuenta_confirmada, e.aforo_publico, e.activo, e.imagen, " +
            "u.id_usuario, u.email, u.nombre AS u_nombre, u.apellidos AS u_apellidos, u.telefono AS u_telefono " +
            "FROM establecimiento e " +
            "INNER JOIN usuario u ON e.id_propietario = u.id_usuario";

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Establecimiento> rowMapper = (rs, rowNum) -> {
        Usuario propietario = new Usuario();
        propietario.setId(rs.getLong("id_usuario"));
        propietario.setEmail(rs.getString("email"));
        propietario.setNombre(rs.getString("u_nombre"));
        propietario.setApellidos(rs.getString("u_apellidos"));
        propietario.setTelefono(rs.getString("u_telefono"));

        Establecimiento e = new Establecimiento();
        e.setId(rs.getLong("id_establecimiento"));
        e.setNombre(rs.getString("nombre"));
        e.setTipo(rs.getString("tipo"));
        e.setDireccion(rs.getString("direccion"));
        e.setDescripcion(rs.getString("descripcion"));
        e.setAforoMaximo(rs.getObject("aforo_maximo", Integer.class));
        e.setPrecioReferencia(rs.getBigDecimal("precio_referencia"));
        e.setEdadMinima(rs.getObject("edad_minima", Integer.class));
        e.setExigeCuentaConfirmada(rs.getBoolean("exige_cuenta_confirmada"));
        e.setAforoPublico(rs.getBoolean("aforo_publico"));
        e.setActivo(rs.getBoolean("activo"));
        e.setImagen(rs.getString("imagen"));
        e.setPropietario(propietario);
        return e;
    };

    public EstablecimientoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Establecimiento> findAll() {
        return jdbcTemplate.query(SELECT + " ORDER BY e.nombre", rowMapper);
    }

    @Override
    public Optional<Establecimiento> findById(Long id) {
        List<Establecimiento> result =
                jdbcTemplate.query(SELECT + " WHERE e.id_establecimiento = ?", rowMapper, id);
        return result.stream().findFirst();
    }

    @Override
    public Long insert(Establecimiento e) {
        final String sql = "INSERT INTO establecimiento (nombre, tipo, direccion, descripcion, aforo_maximo, " +
                "precio_referencia, edad_minima, exige_cuenta_confirmada, aforo_publico, activo, imagen, id_propietario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getTipo());
            ps.setString(3, e.getDireccion());
            setNullableString(ps, 4, e.getDescripcion());
            ps.setInt(5, e.getAforoMaximo());
            ps.setBigDecimal(6, e.getPrecioReferencia());
            setNullableInt(ps, 7, e.getEdadMinima());
            ps.setBoolean(8, e.isExigeCuentaConfirmada());
            ps.setBoolean(9, e.isAforoPublico());
            ps.setBoolean(10, e.isActivo());
            setNullableString(ps, 11, e.getImagen());
            ps.setLong(12, e.getPropietario().getId());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key != null ? key.longValue() : null;
    }

    @Override
    public int update(Establecimiento e) {
        return jdbcTemplate.update(
                "UPDATE establecimiento SET nombre = ?, tipo = ?, direccion = ?, descripcion = ?, aforo_maximo = ?, " +
                "precio_referencia = ?, edad_minima = ?, exige_cuenta_confirmada = ?, aforo_publico = ?, activo = ?, " +
                "imagen = ?, id_propietario = ? WHERE id_establecimiento = ?",
                e.getNombre(), e.getTipo(), e.getDireccion(), e.getDescripcion(), e.getAforoMaximo(),
                e.getPrecioReferencia(), e.getEdadMinima(), e.isExigeCuentaConfirmada(), e.isAforoPublico(),
                e.isActivo(), e.getImagen(), e.getPropietario().getId(), e.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM establecimiento WHERE id_establecimiento = ?", id);
    }

    @Override
    public boolean existsByNombre(String nombre, Long excludeId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM establecimiento WHERE LOWER(nombre) = LOWER(?) AND id_establecimiento <> ?",
                Integer.class, nombre, excludeId == null ? 0L : excludeId);
        return count != null && count > 0;
    }

    private static void setNullableString(PreparedStatement ps, int index, String value) throws java.sql.SQLException {
        if (value == null) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, value);
        }
    }

    private static void setNullableInt(PreparedStatement ps, int index, Integer value) throws java.sql.SQLException {
        if (value == null) {
            ps.setNull(index, Types.INTEGER);
        } else {
            ps.setInt(index, value);
        }
    }
}
