package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sucursales")
// Soft Delete: Al eliminar cambia el estado a 0
@SQLDelete(sql = "UPDATE sucursales SET estado=0 WHERE id_sucursal=?") 
// Filtrado automático: Solo trae registros activos (estado=1)
@Where(clause = "estado=1") 
public class Sucursales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Integer idSucursal;

    // Clave Foránea para Multi-Tenant
    @Column(name = "id_restaurante", nullable = false)
    private Integer idRestaurante;

    @Column(nullable = false, length = 150)
    private String nombre;
    
    @Column(nullable = false)
    private String direccion;
    
    @Column(length = 20)
    private String telefono;
    
    @Column(name = "horario_atencion")
    private String horarioAtencion;
    
    // Estado (1: Activo, 0: Eliminado)
    @Column(nullable = false)
    private Integer estado = 1;

    // Constructor vacío (obligatorio para JPA)
    public Sucursales() {}

    // --- Getters y Setters ---
    
    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
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

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Sucursales [idSucursal=" + idSucursal + ", idRestaurante=" + idRestaurante + 
               ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + 
               ", horarioAtencion=" + horarioAtencion + ", estado=" + estado + "]";
    }
}