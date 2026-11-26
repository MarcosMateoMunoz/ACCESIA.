package org.iesalixar.daw2.MarcosMateoMunoz.accesia.daos;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Establecimiento;

import java.util.List;
import java.util.Optional;

public interface EstablecimientoDAO {

    /** Todos los establecimientos, ya con los datos de su propietario (JOIN). */
    List<Establecimiento> findAll();

    Optional<Establecimiento> findById(Long id);

    /** @return id generado */
    Long insert(Establecimiento establecimiento);

    /** @return filas modificadas */
    int update(Establecimiento establecimiento);

    int deleteById(Long id);

    /** Comprueba si ya existe un establecimiento con ese nombre (ignorando mayúsculas) distinto de excludeId. */
    boolean existsByNombre(String nombre, Long excludeId);
}
