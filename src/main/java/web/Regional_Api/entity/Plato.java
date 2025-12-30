package web.Regional_Api.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "platos")
@SQLDelete(sql = "UPDATE platos SET estado = 0 WHERE id_plato = ?")
@Where(clause = "estado = 1")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_plato;
    
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer estado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursales sucursales;

    public Plato() {
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public Sucursales getSucursales() {
        return sucursales;
    }
    
    public void setSucursales(Sucursales sucursales) {
        this.sucursales = sucursales;
    }

    @Override
    public String toString() {
        return "Plato [id_plato=" + id_plato + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio="
                + precio + ", estado=" + estado + ", categoria=" + categoria + ", sucursales=" + sucursales + "]";
    }
    
}