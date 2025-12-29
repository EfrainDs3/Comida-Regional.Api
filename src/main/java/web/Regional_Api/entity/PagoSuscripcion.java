package web.Regional_Api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos_suscripcion")
public class PagoSuscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurante", nullable = false)
    private Restaurante restaurante;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "periodo_cubierto", length = 100)
    private  String periodoCubierto;

    @Column(name ="metodo_pago", length = 50)
    private String metodoPago;

    @Column(name = "url_comprobante", length = 255)
    private String comprobanteUrl;

    // PENDIENTE, APROBADO, RECHAZADO
    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name="fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    public PagoSuscripcion() {
    }

    public PagoSuscripcion(Long idPago, Restaurante restaurante, BigDecimal monto, LocalDateTime fechaPago, String estado) {
        this.idPago = idPago;
        this.restaurante = restaurante;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
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
    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }
    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    @PrePersist
    public void prePersist() {
        if(this.fechaPago == null) this.fechaPago = LocalDateTime.now();
        if(this.estado == null) this.estado = "PENDIENTE";
    }
    @Override
    public String toString() {
        return "PagoSuscripcion [idPago=" + idPago + ", restaurante=" + restaurante + ", monto=" + monto
                + ", fechaPago=" + fechaPago + ", periodoCubierto=" + periodoCubierto + ", metodoPago=" + metodoPago
                + ", comprobanteUrl=" + comprobanteUrl + ", estado=" + estado + ", fechaAprobacion=" + fechaAprobacion
                + ", observaciones=" + observaciones + "]";
    }
}