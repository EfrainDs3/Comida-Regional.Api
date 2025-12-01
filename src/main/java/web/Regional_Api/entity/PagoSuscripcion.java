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
    private BigDecimal monto_pagado;

    @Column(name = "periodo_cubierto_inicio", columnDefinition = "date")
    private LocalDate fecha_inicio_suscripcion;

    @Column(name = "periodo_cubierto_fin", columnDefinition = "date")
    private LocalDate fecha_fin_suscripcion;

    @Column(nullable = false) 
    @ColumnDefault("1")
    private Integer estado_pago = 1; // Inicializado para evitar nulls

    // --- Getters y Setters ---

    public Integer getId_pago() { return id_pago; }
    public void setId_pago(Integer id_pago) { this.id_pago = id_pago; }

    public Restaurante getRestaurante() { return restaurante; }
    public void setRestaurante(Restaurante restaurante) { this.restaurante = restaurante; }

    public LocalDate getFecha_pago() { return fecha_pago; }
    public void setFecha_pago(LocalDate fecha_pago) { this.fecha_pago = fecha_pago; }

    public BigDecimal getMonto_pagado() { return monto_pagado; }
    public void setMonto_pagado(BigDecimal monto_pagado) { this.monto_pagado = monto_pagado; }

    public LocalDate getFecha_inicio_suscripcion() { return fecha_inicio_suscripcion; }
    public void setFecha_inicio_suscripcion(LocalDate fecha_inicio_suscripcion) { this.fecha_inicio_suscripcion = fecha_inicio_suscripcion; }

    public LocalDate getFecha_fin_suscripcion() { return fecha_fin_suscripcion; }
    public void setFecha_fin_suscripcion(LocalDate fecha_fin_suscripcion) { this.fecha_fin_suscripcion = fecha_fin_suscripcion; }

    public Integer getEstado_pago() { return estado_pago; }
    public void setEstado_pago(Integer estado_pago) { this.estado_pago = estado_pago; }
}