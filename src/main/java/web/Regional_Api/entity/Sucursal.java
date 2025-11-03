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
@Table(name = "sucursales")
@SQLDelete(sql = "UPDATE sucursales SET estado = 0 WHERE id_sucursal = ?")
@Where(clause = "estado = 1")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sucursal;
    
    private String nombre;
    private String direccion;
    private String telefono;
    private String ciudad;
    private String estado_sucursal; // Activo, Inactivo
    private Integer estado = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurante")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Restaurante id_restaurante;

    public Sucursal() {}

    public Sucursal(Integer id) {
        this.id_sucursal = id;
    }

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado_sucursal() {
        return estado_sucursal;
    }

    public void setEstado_sucursal(String estado_sucursal) {
        this.estado_sucursal = estado_sucursal;
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
        return "Sucursal [id_sucursal=" + id_sucursal + ", nombre=" + nombre + ", direccion=" + direccion
                + ", estado=" + estado + "]";
    }
}
