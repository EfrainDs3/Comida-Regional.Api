package web.Regional_Api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idCategoria;
    private Integer idRestaurante;
    private String nombre;
    private String descripcion;
    private String icono;
    private Integer estado = 1;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructores
    public CategoriaDTO() {}

    public CategoriaDTO(Integer idCategoria, Integer idRestaurante, String nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
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
        return "CategoriaDTO [idCategoria=" + idCategoria + ", idRestaurante=" + idRestaurante
                + ", nombre=" + nombre + ", estado=" + estado + "]";
    }
}
