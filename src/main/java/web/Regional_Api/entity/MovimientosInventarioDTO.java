package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimientosInventarioDTO {
    
    private Integer id_movimiento;
    private BigDecimal cantidad;
    private String motivo;
    private String tipo_movimiento; // "Entrada", "Salida" o "Ajuste"
    private LocalDateTime fecha_movimiento;
    private Integer idInsumo;
    private Integer idUsuario;
    private Integer idProveedor; // Nullable

    // --- Getters y Setters ---
    
    public Integer getId_movimiento() {
        return id_movimiento;
    }
    public void setId_movimiento(Integer id_movimiento) {
        this.id_movimiento = id_movimiento;
    }
    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public String getTipo_movimiento() {
        return tipo_movimiento;
    }
    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }
    public LocalDateTime getFecha_movimiento() {
        return fecha_movimiento;
    }
    public void setFecha_movimiento(LocalDateTime fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }
    public Integer getIdInsumo() {
        return idInsumo;
    }
    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Integer getIdProveedor() {
        return idProveedor;
    }
    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }
} 
    

