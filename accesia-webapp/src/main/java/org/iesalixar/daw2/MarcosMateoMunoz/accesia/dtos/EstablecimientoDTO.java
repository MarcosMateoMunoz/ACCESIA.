package org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Datos del formulario de alta / modificación de Establecimiento.
 * Los mensajes de error se resuelven por código desde messages_es / messages_en
 * (p. ej. NotBlank.establecimiento.nombre).
 */
public class EstablecimientoDTO {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank
    private String tipo;

    @NotBlank
    @Size(max = 200)
    private String direccion;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    @Min(1)
    @Max(100000)
    private Integer aforoMaximo;

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal precioReferencia;

    @Min(0)
    @Max(99)
    private Integer edadMinima;

    private boolean exigeCuentaConfirmada;
    private boolean aforoPublico = true;
    private boolean activo = true;

    /** Nombre del fichero de imagen actual (solo para mostrarlo en la edición). */
    private String imagen;

    /** Clave ajena hacia USUARIO: el propietario seleccionado en el <select>. */
    @NotNull
    private Long idPropietario;

    public EstablecimientoDTO() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getAforoMaximo() { return aforoMaximo; }
    public void setAforoMaximo(Integer aforoMaximo) { this.aforoMaximo = aforoMaximo; }

    public BigDecimal getPrecioReferencia() { return precioReferencia; }
    public void setPrecioReferencia(BigDecimal precioReferencia) { this.precioReferencia = precioReferencia; }

    public Integer getEdadMinima() { return edadMinima; }
    public void setEdadMinima(Integer edadMinima) { this.edadMinima = edadMinima; }

    public boolean isExigeCuentaConfirmada() { return exigeCuentaConfirmada; }
    public void setExigeCuentaConfirmada(boolean exigeCuentaConfirmada) { this.exigeCuentaConfirmada = exigeCuentaConfirmada; }

    public boolean isAforoPublico() { return aforoPublico; }
    public void setAforoPublico(boolean aforoPublico) { this.aforoPublico = aforoPublico; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
}
