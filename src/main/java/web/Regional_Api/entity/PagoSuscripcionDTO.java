package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoSuscripcionDTO {
    
    private Integer id_pago;
    
    private Integer id_restaurante; 
    
    private LocalDate fecha_pago;
    private BigDecimal monto_pagado;
    private String tipo_suscripcion;
    private LocalDate fecha_inicio_suscripcion;
    private LocalDate fecha_fin_suscripcion;
    private String metodo_pago;
    private Integer estado_pago;
    
    public Integer getId_pago() {
        return id_pago;
    }
    public void setId_pago(Integer id_pago) {
        this.id_pago = id_pago;
    }
    public Integer getId_restaurante() {
        return id_restaurante;
    }
    public void setId_restaurante(Integer id_restaurante) {
        this.id_restaurante = id_restaurante;
    }
    public LocalDate getFecha_pago() {
        return fecha_pago;
    }
    public void setFecha_pago(LocalDate fecha_pago) {
        this.fecha_pago = fecha_pago;
    }
    public BigDecimal getMonto_pagado() {
        return monto_pagado;
    }
    public void setMonto_pagado(BigDecimal monto_pagado) {
        this.monto_pagado = monto_pagado;
    }
    public String getTipo_suscripcion() {
        return tipo_suscripcion;
    }
    public void setTipo_suscripcion(String tipo_suscripcion) {
        this.tipo_suscripcion = tipo_suscripcion;
    }
    public LocalDate getFecha_inicio_suscripcion() {
        return fecha_inicio_suscripcion;
    }
    public void setFecha_inicio_suscripcion(LocalDate fecha_inicio_suscripcion) {
        this.fecha_inicio_suscripcion = fecha_inicio_suscripcion;
    }
    public LocalDate getFecha_fin_suscripcion() {
        return fecha_fin_suscripcion;
    }
    public void setFecha_fin_suscripcion(LocalDate fecha_fin_suscripcion) {
        this.fecha_fin_suscripcion = fecha_fin_suscripcion;
    }
    public String getMetodo_pago() {
        return metodo_pago;
    }
    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }
    public Integer getEstado_pago() {
        return estado_pago;
    }
    public void setEstado_pago(Integer estado_pago) {
        this.estado_pago = estado_pago;
    }
}