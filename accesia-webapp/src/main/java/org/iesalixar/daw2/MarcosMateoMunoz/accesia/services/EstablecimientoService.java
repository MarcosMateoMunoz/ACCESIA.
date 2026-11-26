package org.iesalixar.daw2.MarcosMateoMunoz.accesia.services;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos.EstablecimientoDTO;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Establecimiento;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EstablecimientoService {

    List<Establecimiento> listar();

    /** @throws org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions.ResourceNotFoundException si no existe */
    Establecimiento obtener(Long id);

    /** @throws org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions.DuplicateResourceException si el nombre ya existe */
    Establecimiento crear(EstablecimientoDTO dto, MultipartFile imagen);

    Establecimiento actualizar(Long id, EstablecimientoDTO dto, MultipartFile imagen);

    void eliminar(Long id);
}
