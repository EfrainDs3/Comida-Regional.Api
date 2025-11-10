package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
@DynamicInsert
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pedido;

    // --- Relaciones Fieles a FOREIGN KEYs (Módulos de compañeros) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursales sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mesa")
    private Mesas mesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_mozo")
    private Usuarios usuarioMozo; 


    @CreationTimestamp 
    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha_hora_pedido; 

    @Column(length = 20, nullable = false)
    @ColumnDefault("'Pendiente'") 
    private String estado_pedido;

    @Column(precision = 10, scale = 2)
    private BigDecimal total_pedido;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Mesas getMesa() {
        return mesa;
    }

    public void setMesa(Mesas mesa) {
        this.mesa = mesa;
    }

    public Usuarios getUsuarioMozo() {
        return usuarioMozo;
    }

    public void setUsuarioMozo(Usuarios usuarioMozo) {
        this.usuarioMozo = usuarioMozo;
    }

    public LocalDateTime getFecha_hora_pedido() {
        return fecha_hora_pedido;
    }

    public void setFecha_hora_pedido(LocalDateTime fecha_hora_pedido) {
        this.fecha_hora_pedido = fecha_hora_pedido;
    }

    public String getEstado_pedido() {
        return estado_pedido;
    }

    public void setEstado_pedido(String estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    public BigDecimal getTotal_pedido() {
        return total_pedido;
    }

    public void setTotal_pedido(BigDecimal total_pedido) {
        this.total_pedido = total_pedido;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Pedido [id_pedido=" + id_pedido + ", sucursal=" + sucursal + ", mesa=" + mesa + ", usuarioMozo="
                + usuarioMozo + ", fecha_hora_pedido=" + fecha_hora_pedido + ", estado_pedido=" + estado_pedido
                + ", total_pedido=" + total_pedido + ", detalles=" + detalles + "]";
    }

   
}
