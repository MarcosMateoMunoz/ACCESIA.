package org.iesalixar.daw2.MarcosMateoMunoz.accesia.daos;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {

    /** Usuarios activos, ordenados por nombre (para rellenar el desplegable del formulario). */
    List<Usuario> findAllActivos();

    Optional<Usuario> findById(Long id);

    boolean existsActivoById(Long id);
}
