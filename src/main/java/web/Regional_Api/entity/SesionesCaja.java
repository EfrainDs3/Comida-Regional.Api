package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sesiones_caja")
@SQLDelete(sql = "UPDATE sesiones_caja SET estado=0 WHERE id_sesion=?") 
@Where(clause = "estado=1") 
public class SesionesCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Integer idSesion;

    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "monto_inicial", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoInicial;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura = LocalDateTime.now(); 

    @Column(name = "id_usuario_cierre")
    private Integer idUsuarioCierre;

    @Column(name = "monto_final_calculado", precision = 10, scale = 2)
    private BigDecimal montoFinalCalculado;

    @Column(name = "monto_final_real", precision = 10, scale = 2)
    private BigDecimal montoFinalReal;

    private BigDecimal diferencia;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre; 

    @Column(nullable = false)
    private Integer estado = 1;

   
    public SesionesCaja() {}
    
    public Integer getIdSesion() { return idSesion; }
    public void setIdSesion(Integer idSesion) { this.idSesion = idSesion; }
    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public BigDecimal getMontoInicial() { return montoInicial; }
    public void setMontoInicial(BigDecimal montoInicial) { this.montoInicial = montoInicial; }
    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDateTime fechaApertura) { this.fechaApertura = fechaApertura; }
    public Integer getIdUsuarioCierre() { return idUsuarioCierre; }
    public void setIdUsuarioCierre(Integer idUsuarioCierre) { this.idUsuarioCierre = idUsuarioCierre; }
    public BigDecimal getMontoFinalCalculado() { return montoFinalCalculado; }
    public void setMontoFinalCalculado(BigDecimal montoFinalCalculado) { this.montoFinalCalculado = montoFinalCalculado; }
    public BigDecimal getMontoFinalReal() { return montoFinalReal; }
    public void setMontoFinalReal(BigDecimal montoFinalReal) { this.montoFinalReal = montoFinalReal; }
    public BigDecimal getDiferencia() { return diferencia; }
    public void setDiferencia(BigDecimal diferencia) { this.diferencia = diferencia; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "SesionesCaja [idSesion=" + idSesion + ", idSucursal=" + idSucursal + ", idUsuario=" + idUsuario
                + ", montoInicial=" + montoInicial + ", fechaApertura=" + fechaApertura + ", idUsuarioCierre="
                + idUsuarioCierre + ", montoFinalCalculado=" + montoFinalCalculado + ", montoFinalReal="
                + montoFinalReal + ", diferencia=" + diferencia + ", fechaCierre=" + fechaCierre + ", estado=" + estado
                + "]";
    }
}