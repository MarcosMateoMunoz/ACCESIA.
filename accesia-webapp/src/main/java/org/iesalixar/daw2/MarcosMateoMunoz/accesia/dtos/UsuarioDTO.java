package org.iesalixar.daw2.MarcosMateoMunoz.accesia.dtos;

/** Datos mínimos del usuario para mostrarlo en el desplegable del formulario. */
public class UsuarioDTO {

    private Long id;
    private String nombreCompleto;
    private String email;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombreCompleto, String email) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
