package web.Regional_Api.entity;

import java.math.BigDecimal;

public class PlatoDTO {
    private Integer id_plato;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imagen_url;
    private Integer disponible;
    private Integer id_categoria;
    private Integer id_sucursal;
    private Integer estado;

    public PlatoDTO() {}

    public PlatoDTO(Integer id_plato, String nombre, String descripcion, BigDecimal precio,
                    String imagen_url, Integer disponible, Integer id_categoria, Integer id_sucursal, Integer estado) {
        this.id_plato = id_plato;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen_url = imagen_url;
        this.disponible = disponible;
        this.id_categoria = id_categoria;
        this.id_sucursal = id_sucursal;
        this.estado = estado;
    }

    public Integer getId_plato() {
        return id_plato;
    }

    public void setId_plato(Integer id_plato) {
        this.id_plato = id_plato;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }

    public Integer getDisponible() {
        return disponible;
    }

    public void setDisponible(Integer disponible) {
        this.disponible = disponible;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PlatoDTO [id_plato=" + id_plato + ", nombre=" + nombre + ", precio=" + precio + "]";
    }
}
