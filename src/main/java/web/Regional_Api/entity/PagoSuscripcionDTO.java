package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoSuscripcionDTO {
    
    private Integer id_pago;
    
    private Integer id_restaurante; 
    
    private LocalDate fecha_pago;
    private BigDecimal monto;
    private LocalDate periodo_cubierto_inicio;
    private LocalDate periodo_cubierto_fin;
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
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public LocalDate getPeriodo_cubierto_inicio() {
        return periodo_cubierto_inicio;
    }
    public void setPeriodo_cubierto_inicio(LocalDate periodo_cubierto_inicio) {
        this.periodo_cubierto_inicio = periodo_cubierto_inicio;
    }
    public LocalDate getPeriodo_cubierto_fin() {
        return periodo_cubierto_fin;
    }
    public void setPeriodo_cubierto_fin(LocalDate periodo_cubierto_fin) {
        this.periodo_cubierto_fin = periodo_cubierto_fin;
    }
    public Integer getEstado_pago() {
        return estado_pago;
    }
    public void setEstado_pago(Integer estado_pago) {
        this.estado_pago = estado_pago;
    }
}