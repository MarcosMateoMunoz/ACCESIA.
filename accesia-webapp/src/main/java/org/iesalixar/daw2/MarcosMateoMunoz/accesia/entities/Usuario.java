package org.iesalixar.daw2.MarcosMateoMunoz.accesia.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Entidad relacionada (lado "1" de la relación 1:N): un Usuario es propietario de varios Establecimientos.
 */
public class Usuario {

    private Long id;
    private String email;
    private String nombre;
    private String apellidos;
    private String password;          // hash BCrypt
    private String telefono;
    private LocalDate fechaNacimiento;
    private String idioma;
    private boolean activo;
    private boolean cuentaConfirmada;
    private LocalDateTime fechaRegistro;

    public Usuario() {
    }

    /** Atributo derivado: se calcula a partir de la fecha de nacimiento. */
    public Integer getEdad() {
        if (fechaNacimiento == null) {
            return null;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getNombreCompleto() {
        return (nombre == null ? "" : nombre) + (apellidos == null ? "" : " " + apellidos);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public boolean isCuentaConfirmada() { return cuentaConfirmada; }
    public void setCuentaConfirmada(boolean cuentaConfirmada) { this.cuentaConfirmada = cuentaConfirmada; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
