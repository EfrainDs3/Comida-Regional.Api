package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedores")
@SQLDelete(sql = " UPDATE proveedores SET estado=0 WHERE id_proveedor=?")
@Where(clause = "estado=1")

public class Proveedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_proveedor;
    private String nombre;
    private String ruc;
    private String contacto_nombre;
    private String contacto_telefono;
    private Integer estado=1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurante")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Restaurante id_restaurante;

    public Integer getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Integer id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public String getContacto_nombre() {
        return contacto_nombre;
    }

    public void setContacto_nombre(String contacto_nombre) {
        this.contacto_nombre = contacto_nombre;
    }

    public String getContacto_telefono() {
        return contacto_telefono;
    }

    public void setContacto_telefono(String contacto_telefono) {
        this.contacto_telefono = contacto_telefono;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Restaurante getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(Restaurante id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    @Override
    public String toString() {
        return "Proveedores [id_proveedor=" + id_proveedor + ", nombre=" + nombre + ", ruc=" + ruc
                + ", contacto_nombre=" + contacto_nombre + ", contacto_telefono=" + contacto_telefono + ", estado="
                + estado + ", id_restaurante=" + id_restaurante + "]";
    }
   
    

    
}
