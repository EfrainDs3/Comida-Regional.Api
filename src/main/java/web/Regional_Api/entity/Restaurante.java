package web.Regional_Api.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurantes")
@SQLDelete(sql = "UPDATE restaurantes SET estado = 0 WHERE id_restaurante = ?")
@Where(clause = "estado = 1")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_restaurante;
    
    private String razon_social;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private String horario_apertura;
    private String horario_cierre;
    private LocalDateTime fecha_afiliacion;
    private Integer estado_pago; // 1: Al día, 2: Pendiente, 3: Cancelado
    private Integer estado = 1;

    public Restaurante() {}

    public Restaurante(Integer id) {
        this.id_restaurante = id;
    }

    public Integer getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(Integer id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHorario_apertura() {
        return horario_apertura;
    }

    public void setHorario_apertura(String horario_apertura) {
        this.horario_apertura = horario_apertura;
    }

    public String getHorario_cierre() {
        return horario_cierre;
    }

    public void setHorario_cierre(String horario_cierre) {
        this.horario_cierre = horario_cierre;
    }

    public LocalDateTime getFecha_afiliacion() {
        return fecha_afiliacion;
    }

    public void setFecha_afiliacion(LocalDateTime fecha_afiliacion) {
        this.fecha_afiliacion = fecha_afiliacion;
    }

    public Integer getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(Integer estado_pago) {
        this.estado_pago = estado_pago;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Restaurante [id_restaurante=" + id_restaurante + ", razon_social=" + razon_social + ", ruc=" + ruc
                + ", direccion=" + direccion + ", estado=" + estado + "]";
    }
}
