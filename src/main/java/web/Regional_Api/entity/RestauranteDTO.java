package web.Regional_Api.entity;

import java.math.BigDecimal;

public class RestauranteDTO {

    private String razon_social;
    private String ruc;
    private String direccion_principal;
    private String logo_url;
    private String moneda;
    private String simbolo_moneda;
    private BigDecimal tasa_igv;
    private String email;

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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
