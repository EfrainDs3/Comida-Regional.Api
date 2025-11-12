package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos_suscripcion")
@DynamicInsert 
public class PagoSuscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pago;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_restaurante", nullable = false) 
    private Restaurante restaurante;

    @Column(columnDefinition = "date") 
    private LocalDate fecha_pago;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "periodo_cubierto_inicio", columnDefinition = "date")
    private LocalDate periodoCubiertoInicio;

    @Column(name = "periodo_cubierto_fin", columnDefinition = "date")
    private LocalDate periodoCubiertoFin;

    @Column(nullable = false) 
    @ColumnDefault("1")
    private Integer estado_pago=1;
    
    public Integer getId_pago() {
        return id_pago;
    }
    public void setId_pago(Integer id_pago) {
        this.id_pago = id_pago;
    }
    public Restaurante getRestaurante() {
        return restaurante;
    }
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
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
    public LocalDate getPeriodoCubiertoInicio() {
        return periodoCubiertoInicio;
    }
    public void setPeriodoCubiertoInicio(LocalDate periodoCubiertoInicio) {
        this.periodoCubiertoInicio = periodoCubiertoInicio;
    }
    public LocalDate getPeriodoCubiertoFin() {
        return periodoCubiertoFin;
    }
    public void setPeriodoCubiertoFin(LocalDate periodoCubiertoFin) {
        this.periodoCubiertoFin = periodoCubiertoFin;
    }
    public Integer getEstado_pago() {
        return estado_pago;
    }
    public void setEstado_pago(Integer estado_pago) {
        this.estado_pago = estado_pago;
    }
}