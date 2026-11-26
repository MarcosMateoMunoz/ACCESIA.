package org.iesalixar.daw2.MarcosMateoMunoz.accesia.services;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.daos.EstablecimientoDAO;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos.EstablecimientoDTO;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Establecimiento;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions.DuplicateResourceException;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.mappers.EstablecimientoMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EstablecimientoServiceImpl implements EstablecimientoService {

    private final EstablecimientoDAO establecimientoDAO;
    private final EstablecimientoMapper mapper;
    private final FileStorageService fileStorage;

    public EstablecimientoServiceImpl(EstablecimientoDAO establecimientoDAO,
                                      EstablecimientoMapper mapper,
                                      FileStorageService fileStorage) {
        this.establecimientoDAO = establecimientoDAO;
        this.mapper = mapper;
        this.fileStorage = fileStorage;
    }

    @Override
    public List<Establecimiento> listar() {
        return establecimientoDAO.findAll();
    }

    @Override
    public Establecimiento obtener(Long id) {
        return establecimientoDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento no encontrado: " + id));
    }

    @Override
    public Establecimiento crear(EstablecimientoDTO dto, MultipartFile imagen) {
        if (establecimientoDAO.existsByNombre(dto.getNombre().trim(), null)) {
            throw new DuplicateResourceException("Ya existe un establecimiento con el nombre " + dto.getNombre());
        }
        Establecimiento establecimiento = mapper.toEntity(dto);
        establecimiento.setId(null);
        establecimiento.setImagen(null);

        boolean hayImagen = imagen != null && !imagen.isEmpty();
        if (hayImagen) {
            establecimiento.setImagen(fileStorage.store(imagen));
        }
        try {
            establecimiento.setId(establecimientoDAO.insert(establecimiento));
            return establecimiento;
        } catch (RuntimeException ex) {
            // si falla la BD no dejamos la imagen huérfana
            fileStorage.delete(establecimiento.getImagen());
            throw ex;
        }
    }

    @Override
    public Establecimiento actualizar(Long id, EstablecimientoDTO dto, MultipartFile imagen) {
        Establecimiento existente = obtener(id);
        if (establecimientoDAO.existsByNombre(dto.getNombre().trim(), id)) {
            throw new DuplicateResourceException("Ya existe un establecimiento con el nombre " + dto.getNombre());
        }
        Establecimiento establecimiento = mapper.toEntity(dto);
        establecimiento.setId(id);
        establecimiento.setImagen(existente.getImagen());

        boolean hayImagenNueva = imagen != null && !imagen.isEmpty();
        if (hayImagenNueva) {
            establecimiento.setImagen(fileStorage.store(imagen));
        }
        try {
            if (establecimientoDAO.update(establecimiento) == 0) {
                throw new ResourceNotFoundException("Establecimiento no encontrado: " + id);
            }
        } catch (RuntimeException ex) {
            if (hayImagenNueva) {
                fileStorage.delete(establecimiento.getImagen());
            }
            throw ex;
        }
        if (hayImagenNueva) {
            fileStorage.delete(existente.getImagen());
        }
        return establecimiento;
    }

    @Override
    public void eliminar(Long id) {
        Establecimiento existente = obtener(id);
        establecimientoDAO.deleteById(id);
        fileStorage.delete(existente.getImagen());
    }
}
