package com.comidas.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_caja")
public class MovimientosCaja {

    // Simulación del ENUM tipo_movimiento de MySQL
    public enum TipoMovimiento {
        Ingreso, Egreso
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento_caja")
    private Integer idMovimientoCaja;

    // Clave Foránea a la Sesión de Caja (debe estar abierta)
    @Column(name = "id_sesion", nullable = false)
    private Integer idSesion;

    // Usuario que registra el movimiento
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Enumerated(EnumType.STRING) // Mapeo del ENUM de MySQL
    @Column(name = "tipo_movimiento", nullable = false, length = 7)
    private TipoMovimiento tipoMovimiento;

    @Column(nullable = false, length = 200)
    private String concepto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    public MovimientosCaja() {}

    // --- Getters y Setters ---

    public Integer getIdMovimientoCaja() { return idMovimientoCaja; }
    public void setIdMovimientoCaja(Integer idMovimientoCaja) { this.idMovimientoCaja = idMovimientoCaja; }
    public Integer getIdSesion() { return idSesion; }
    public void setIdSesion(Integer idSesion) { this.idSesion = idSesion; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }
    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }
}