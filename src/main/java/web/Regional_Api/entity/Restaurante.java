package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurantes")
@DynamicInsert // Usa los DEFAULT de la BD
@SQLDelete(sql = "UPDATE restaurantes SET estado = 0 WHERE id_restaurante = ?")
//@Where(clause = "estado = 1") // Solo trae los activos
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_restaurante;

    @Column(length = 255, nullable = false)
    private String razon_social;

    @Column(length = 11, nullable = false, unique = true)
    private String ruc;

    @Column(columnDefinition = "TEXT")
    private String direccion_principal;

    @Column(length = 255)
    private String logo_url;

    @Column(length = 10)
    @ColumnDefault("'PEN'")
    private String moneda;

    @Column(length = 5)
    @ColumnDefault("'S/'")
    private String simbolo_moneda;

    @Column(precision = 5, scale = 2)
    @ColumnDefault("18.00")
    private BigDecimal tasa_igv;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer estado=1;

    @CreationTimestamp
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
}