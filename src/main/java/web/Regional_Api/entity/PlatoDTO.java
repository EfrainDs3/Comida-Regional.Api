package web.Regional_Api.entity;

import java.math.BigDecimal;

public class PlatoDTO {
    private Integer id_plato;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer id_categoria;
    private Integer id_sucursal;
    private Integer estado;

    public PlatoDTO() {}

    public PlatoDTO(Integer id_plato, String nombre, String descripcion, BigDecimal precio, Integer id_categoria, Integer estado) {
        this.id_plato = id_plato;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id_categoria = id_categoria;
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
}
