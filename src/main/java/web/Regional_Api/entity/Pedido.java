package web.Regional_Api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuarios usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    @JsonIgnore
    private Sucursales sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mesa")
    @JsonIgnore
    private Mesas mesa;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaPedido;

    // PENDIENTE, EN_PREPARACION, SERVIDO, PAGADO, CANCELADO
    @Column(name = "estado_pedido", length = 20)
    private String estado;

    @Column(name = "monto_total", precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "tipo_pedido", length = 500)
    private String tipoPedido;

    @Column(name = "nombre_cliente", length = 500)
    private String nombreCliente;

    @Column(name = "fecha_update", nullable= false)
    private LocalDateTime fechaUpdate;

    @Column(name = "telefono_cliente", length = 10)
    private String telefonoCliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DetallePedido> detalles;

    public Pedido() {
    }

    // Getters y Setters
    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Sucursales getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursales sucursal) {
        this.sucursal = sucursal;
    }

    public Mesas getMesa() {
        return mesa;
    }

    public void setMesa(Mesas mesa) {
        this.mesa = mesa;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }
    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    public LocalDateTime getFechaUpdate() {
        return fechaUpdate;
    }
    public void setFechaUpdate(LocalDateTime fechaUpdate) {
        this.fechaUpdate = fechaUpdate;
    }
    public String getTelefonoCliente() {
        return telefonoCliente;
    }
    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
    
    @PrePersist
    public void prePersist() {
        if(this.fechaPedido == null) this.fechaPedido = LocalDateTime.now();
        if(this.total == null) this.total = BigDecimal.ZERO;
        if(this.fechaUpdate == null) this.fechaUpdate = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido + ", usuario=" + usuario + ", sucursal=" + sucursal + ", mesa=" + mesa
                + ", fechaPedido=" + fechaPedido + ", estado=" + estado + ", total=" + total + ", tipoPedido="
                + tipoPedido + ", nombreCliente=" + nombreCliente + ", fechaUpdate=" + fechaUpdate
                + ", telefonoCliente=" + telefonoCliente + ", detalles=" + detalles + "]";
    }
}