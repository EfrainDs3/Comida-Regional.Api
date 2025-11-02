package web.Regional_Api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OpcionAdicionalDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idOpcion;
    private Integer idPlato;
    private String nombre;
    private BigDecimal precio;
    private Integer estado = 1;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructores
    public OpcionAdicionalDTO() {}

    public OpcionAdicionalDTO(Integer idOpcion, Integer idPlato, String nombre, BigDecimal precio) {
        this.idOpcion = idOpcion;
        this.idPlato = idPlato;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters
    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Integer getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Integer idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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
        return "OpcionAdicionalDTO [idOpcion=" + idOpcion + ", idPlato=" + idPlato
                + ", nombre=" + nombre + ", precio=" + precio + ", estado=" + estado + "]";
    }
}

