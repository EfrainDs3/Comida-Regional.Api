package web.Regional_Api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurantes")
public class Restaurante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurante")
    private Integer idRestaurante;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "ruc", length = 20)
    private String ruc;

    @Column(name = "direccion_principal", length = 200)
    private String direccion;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(name = "simbolo_moneda", length = 10)
    private String simboloMoneda;

    @Column(name = "tasa_igv", precision = 5, scale = 2)
    private BigDecimal tasaIgv;

    @Column(name = "email_contacto", length = 100)
    private String emailContacto;

    // 1: Activo, 0: Inactivo
    @Column(name = "estado")
    private Integer estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDateTime fechaVencimiento;

    // Relación con Sucursales (Visible en tus FKs: sucursales -> restaurantes)
    // Se usa 'cascade' para que al borrar el restaurante se borren sus sucursales
    // si es necesario
    @JsonIgnore
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sucursales> sucursales;

    // Relación con Pagos de Suscripción
    @JsonIgnore
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PagoSuscripcion> pagosSuscripcion;

    public Restaurante() {
    }

    public Restaurante(Integer idRestaurante, String nombre, String direccion, String telefono, Integer estado) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.direccion = direccion;
        this.estado = estado;
    }

    // Getters y Setters
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public List<Sucursales> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursales> sucursales) {
        this.sucursales = sucursales;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSimboloMoneda() {
        return simboloMoneda;
    }

    public void setSimboloMoneda(String simboloMoneda) {
        this.simboloMoneda = simboloMoneda;
    }

    public BigDecimal getTasaIgv() {
        return tasaIgv;
    }

    public void setTasaIgv(BigDecimal tasaIgv) {
        this.tasaIgv = tasaIgv;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public List<PagoSuscripcion> getPagosSuscripcion() {
        return pagosSuscripcion;
    }

    public void setPagosSuscripcion(List<PagoSuscripcion> pagosSuscripcion) {
        this.pagosSuscripcion = pagosSuscripcion;
    }

    @Override
    public String toString() {
        return "Restaurante [idRestaurante=" + idRestaurante + ", nombre=" + nombre + ",ruc=" + ruc + ", direccion="
                + direccion
                + ", logoUrl=" + logoUrl + ", simboloMoneda=" + simboloMoneda + ", tasaIgv=" + tasaIgv
                + ", emailContacto=" + emailContacto + ", estado=" + estado
                + ", fechaCreacion=" + fechaCreacion + ", fechaVencimiento=" + fechaVencimiento + "]";
    }
}