package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "opciones_adicionales")
@SQLDelete(sql = "UPDATE opciones_adicionales SET estado = 0 WHERE id_opcion = ?")
@Where(clause = "estado = 1")
public class OpcionAdicional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOpcion;
    
    private String nombre;
    private Double precio;
    private Integer estado = 1;

    public OpcionAdicional() {}

    public OpcionAdicional(Integer id) {
        this.idOpcion = id;
    }

    // Getters and Setters
    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "OpcionAdicional [idOpcion=" + idOpcion + ", nombre=" + nombre + ", estado=" + estado + "]";
    }
}

