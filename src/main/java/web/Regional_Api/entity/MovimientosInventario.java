package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "movimientos_inventario")
public class MovimientosInventario {

    // --- ENUM ANIDADO (como lo pediste) ---
    public enum TipoMovimiento {
        Entrada,
        Salida,
        Ajuste
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_movimiento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    private String motivo;

    @Enumerated(EnumType.STRING) // Usa el nombre del Enum
    @Column(nullable = false)
    private TipoMovimiento tipo_movimiento;

    @CreationTimestamp // Se asigna automáticamente al crear
    @Column(updatable = false)
    private LocalDateTime fecha_movimiento;

    // --- Relaciones ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_insumo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Insumos insumo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Usuarios usuario; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = true) // Es nullable
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Proveedores proveedor;

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
    public TipoMovimiento getTipo_movimiento() {
        return tipo_movimiento;
    }
    public void setTipo_movimiento(TipoMovimiento tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }
    public LocalDateTime getFecha_movimiento() {
        return fecha_movimiento;
    }
    public void setFecha_movimiento(LocalDateTime fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }
    public Insumos getInsumo() {
        return insumo;
    }
    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }
    public Usuarios getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    public Proveedores getProveedor() {
        return proveedor;
    }
    public void setProveedor(Proveedores proveedor) {
        this.proveedor = proveedor;
    }
}