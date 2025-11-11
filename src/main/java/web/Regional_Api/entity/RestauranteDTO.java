package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RestauranteDTO {
    
    private Integer id_restaurante;
    private String razon_social;
    private String ruc;
    private String direccion_principal;
    private String logo_url;
    private String moneda;
    private String simbolo_moneda;
    private BigDecimal tasa_igv;
    private Integer estado;
    private LocalDateTime fecha_creacion;

    // --- Getters y Setters ---
    // (Asegúrate de tener todos los Getters y Setters)
    
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