package org.iesalixar.daw2.MarcosMateoMunoz.accesia.services;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.daos.UsuarioDAO;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos.UsuarioDTO;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.mappers.UsuarioMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioDAO usuarioDAO, UsuarioMapper usuarioMapper) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public List<UsuarioDTO> listarActivos() {
        return usuarioMapper.toDTOList(usuarioDAO.findAllActivos());
    }

    @Override
    public boolean existeActivo(Long id) {
        return usuarioDAO.existsActivoById(id);
    }
}
