package web.Regional_Api.entity;

import java.util.Date;

public class PagoSuscripcionDTO {
    private Long idPago;
    private Integer idRestaurante;
    private Double monto;
    private Date fechaPago;
    private String periodoCubierto;
    private String metodoPago;
    private String comprobanteUrl;
    private String estado;
    private String observaciones;
    private Date fechaAprobacion;
    public Long getIdPago() {
        return idPago;
    }
    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }
    public Integer getIdRestaurante() {
        return idRestaurante;
    }
    public void setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
    public Double getMonto() {
        return monto;
    }
    public void setMonto(Double monto) {
        this.monto = monto;
    }
    public Date getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    public String getPeriodoCubierto() {
        return periodoCubierto;
    }
    public void setPeriodoCubierto(String periodoCubierto) {
        this.periodoCubierto = periodoCubierto;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public String getComprobanteUrl() {
        return comprobanteUrl;
    }
    public void setComprobanteUrl(String comprobanteUrl) {
        this.comprobanteUrl = comprobanteUrl;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }
    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    

}
