package org.iesalixar.daw2.MarcosMateoMunoz.accesia.mappers;

import org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos.EstablecimientoDTO;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Establecimiento;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class EstablecimientoMapper {

    /** Entity -> DTO (para rellenar el formulario de edición). */
    public EstablecimientoDTO toDTO(Establecimiento e) {
        EstablecimientoDTO dto = new EstablecimientoDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setTipo(e.getTipo());
        dto.setDireccion(e.getDireccion());
        dto.setDescripcion(e.getDescripcion());
        dto.setAforoMaximo(e.getAforoMaximo());
        dto.setPrecioReferencia(e.getPrecioReferencia());
        dto.setEdadMinima(e.getEdadMinima());
        dto.setExigeCuentaConfirmada(e.isExigeCuentaConfirmada());
        dto.setAforoPublico(e.isAforoPublico());
        dto.setActivo(e.isActivo());
        dto.setImagen(e.getImagen());
        dto.setIdPropietario(e.getPropietario() != null ? e.getPropietario().getId() : null);
        return dto;
    }

    /** DTO -> Entity (para guardar). El propietario solo lleva el id (clave ajena). */
    public Establecimiento toEntity(EstablecimientoDTO dto) {
        Establecimiento e = new Establecimiento();
        e.setId(dto.getId());
        e.setNombre(dto.getNombre() != null ? dto.getNombre().trim() : null);
        e.setTipo(dto.getTipo());
        e.setDireccion(dto.getDireccion() != null ? dto.getDireccion().trim() : null);
        String descripcion = dto.getDescripcion();
        e.setDescripcion(descripcion == null || descripcion.isBlank() ? null : descripcion.trim());
        e.setAforoMaximo(dto.getAforoMaximo());
        e.setPrecioReferencia(dto.getPrecioReferencia());
        e.setEdadMinima(dto.getEdadMinima());
        e.setExigeCuentaConfirmada(dto.isExigeCuentaConfirmada());
        e.setAforoPublico(dto.isAforoPublico());
        e.setActivo(dto.isActivo());
        e.setImagen(dto.getImagen());
        Usuario propietario = new Usuario();
        propietario.setId(dto.getIdPropietario());
        e.setPropietario(propietario);
        return e;
    }
}
