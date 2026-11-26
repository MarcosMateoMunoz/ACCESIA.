package org.iesalixar.daw2.MarcosMateoMunoz.accesia.services;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    /** Usuarios activos que pueden ser propietarios (para el desplegable). */
    List<UsuarioDTO> listarActivos();

    boolean existeActivo(Long id);
}
