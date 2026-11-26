package org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entidad principal del CRUD (lado "N" de la relación 1:N).
 * Muchos Establecimientos pertenecen a un único Usuario (propietario)  ->  @ManyToOne conceptual.
 */
public class Establecimiento {

    /** Valores posibles del atributo codificable "Tipo". */
    public static final List<String> TIPOS =
            List.of("DISCOTECA", "CONCIERTO", "GIMNASIO", "MUSEO", "EVENTO_DEPORTIVO", "OTRO");

    private Long id;
    private String nombre;
    private String tipo;
    private String direccion;
    private String descripcion;
    private Integer aforoMaximo;
    private BigDecimal precioReferencia;
    private Integer edadMinima;
    private boolean exigeCuentaConfirmada;
    private boolean aforoPublico;
    private boolean activo;
    private String imagen;

    /** Relación N:1 -> el propietario (Usuario). */
    private Usuario propietario;

    public Establecimiento() {
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

    public Usuario getPropietario() { return propietario; }
    public void setPropietario(Usuario propietario) { this.propietario = propietario; }
}
