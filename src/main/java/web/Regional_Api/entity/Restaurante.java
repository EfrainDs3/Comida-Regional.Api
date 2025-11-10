package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurantes")
// Soft-Delete usando la columna 'estado' (Fiel al RF03)
@SQLDelete(sql = "UPDATE restaurantes SET estado = 0 WHERE id_restaurante = ?")
@Where(clause = "estado = 1") // JPA solo verá los que tengan estado = 1
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_restaurante;

    @Column(length = 255, nullable = false)
    private String razon_social;

    @Column(length = 11, nullable = false, unique = true)
    private String ruc;

    @Column(columnDefinition = "TEXT") // Fiel al .sql (text)
    private String direccion_principal;

    @Column(length = 255)
    private String logo_url;

    @Column(length = 10)
    @ColumnDefault("'PEN'") // Fiel al .sql (DEFAULT 'PEN')
    private String moneda;

    @Column(length = 5)
    @ColumnDefault("'S/'") // Fiel al .sql (DEFAULT 'S/')
    private String simbolo_moneda;

    @Column(precision = 5, scale = 2) // Fiel al .sql (decimal(5,2))
    @ColumnDefault("18.00") // Fiel al .sql (DEFAULT 18.00)
    private BigDecimal tasa_igv;

    @Column(nullable = false) // Fiel al .sql (tinyint(1) NOT NULL DEFAULT 1)
    @ColumnDefault("1")
    private Integer estado; // Usamos Integer para tinyint(1)

    @CreationTimestamp // Fiel al .sql (DEFAULT current_timestamp())
    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha_creacion;

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

    public String getDireccion_principal() {
        return direccion_principal;
    }

    public void setDireccion_principal(String direccion_principal) {
        this.direccion_principal = direccion_principal;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getSimbolo_moneda() {
        return simbolo_moneda;
    }

    public void setSimbolo_moneda(String simbolo_moneda) {
        this.simbolo_moneda = simbolo_moneda;
    }

    public BigDecimal getTasa_igv() {
        return tasa_igv;
    }

    public void setTasa_igv(BigDecimal tasa_igv) {
        this.tasa_igv = tasa_igv;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @Override
    public String toString() {
        return "Restaurante [id_restaurante=" + id_restaurante + ", razon_social=" + razon_social + ", ruc=" + ruc
                + ", direccion_principal=" + direccion_principal + ", logo_url=" + logo_url + ", moneda=" + moneda
                + ", simbolo_moneda=" + simbolo_moneda + ", tasa_igv=" + tasa_igv + ", estado=" + estado
                + ", fecha_creacion=" + fecha_creacion + "]";
    }

    
}