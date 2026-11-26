package org.iesalixar.daw2.MarcosMateoMunoz.accesia.mappers;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos.UsuarioDTO;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getNombreCompleto(), usuario.getEmail());
    }

    public List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toDTO).toList();
    }
}
