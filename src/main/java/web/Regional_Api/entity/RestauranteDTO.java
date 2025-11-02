package web.Regional_Api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RestauranteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idRestaurante;
    private String nombre;
    private String ruc;
    private String razonSocial;
    private String ubicacion;
    private String telefono;
    private String email;
    private String logo;
    private Integer estado = 1;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructores
    public RestauranteDTO() {}

    public RestauranteDTO(Integer idRestaurante, String nombre, String ruc, String razonSocial,
                         String ubicacion, String telefono, String email, String logo) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.ubicacion = ubicacion;
        this.telefono = telefono;
        this.email = email;
        this.logo = logo;
    }

    // Getters y Setters
    public Integer getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "RestauranteDTO [idRestaurante=" + idRestaurante + ", nombre=" + nombre + ", ruc=" + ruc
                + ", razonSocial=" + razonSocial + ", estado=" + estado + "]";
    }
}
