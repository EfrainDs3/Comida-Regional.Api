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

    @Column(nullable = false, precision = 10, scale = 2) 
    private BigDecimal monto_pagado;

    @Column(length = 50)
    private String tipo_suscripcion;

    @Column(columnDefinition = "date")
    private LocalDate fecha_inicio_suscripcion;
    
    @Column(columnDefinition = "date")
    private LocalDate fecha_fin_suscripcion;

    @Column(length = 50)
    private String metodo_pago;

    @Column(nullable = false) 
    @ColumnDefault("1")
    private Integer estado_pago;
    
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