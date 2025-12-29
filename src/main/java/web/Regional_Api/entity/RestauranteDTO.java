package web.Regional_Api.entity;

import java.math.BigDecimal;

public class RestauranteDTO {
    private Integer idRestaurante;
    private String nombre;
    private String ruc;
    private String direccion;
    private String logoUrl;
    private String simboloMoneda;
    private BigDecimal tasaIgv;
    private String emailContacto;
    private Integer estado;
    private String fechaCreacion;
    private String fechaVencimiento;


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
    public Integer getEstado() {
        return estado;
    }
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
}
