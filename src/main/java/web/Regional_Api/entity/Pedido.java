package web.Regional_Api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
@SQLDelete(sql = "UPDATE pedidos SET estado_pedido = 'Cancelado' WHERE id_pedido = ?")
@SQLRestriction("estado_pedido != 'Cancelado'")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;

    @Column(name = "id_mesa")
    private Integer idMesa;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "nombre_cliente", length = 100)
    private String nombreCliente;

    @Column(name = "tipo_pedido", length = 20)
    private String tipoPedido;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado_pedido", length = 20, nullable = false)
    private String estadoPedido;

    @Column(name = "monto_total", precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "fecha_update", nullable = false)
    private LocalDateTime fechaUpdate;

    @Column(name = "telefono_cliente", length = 20)
    private String telefonoCliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DetallePedido> detalles;

    public Pedido() {
    }

    public Pedido(Integer idSucursal, Integer idUsuario, String nombreCliente, String tipoPedido) {
        this.idSucursal = idSucursal;
        this.idUsuario = idUsuario;
        this.nombreCliente = nombreCliente;
        this.tipoPedido = tipoPedido;
        this.estadoPedido = "Pendiente";
        this.montoTotal = BigDecimal.ZERO;
        this.fechaHora = LocalDateTime.now();
        this.fechaUpdate = LocalDateTime.now();
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
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

    public void agregarDetalle(DetallePedido detalle) {
        this.detalles.add(detalle);
        detalle.setPedido(this);
        this.actualizarMontoTotal();
    }

    public void actualizarMontoTotal() {
        if (detalles != null && !detalles.isEmpty()) {
            this.montoTotal = detalles.stream()
                    .map(DetallePedido::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido + ", idSucursal=" + idSucursal + ", idMesa=" + idMesa
                + ", idUsuario=" + idUsuario + ", nombreCliente=" + nombreCliente + ", estadoPedido=" + estadoPedido
                + ", montoTotal=" + montoTotal + "]";
    }
}
