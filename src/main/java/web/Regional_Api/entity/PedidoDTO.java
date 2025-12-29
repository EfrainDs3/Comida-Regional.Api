package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    private Integer idPedido;
    private Integer idUsuario;
    private Integer idSucursal;
    private Integer idMesa;
    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal total;
    private String tipoPedido;
    private String nombreCliente;
    private LocalDateTime fechaUpdate;
    private String telefonoCliente;
    private List<DetallePedidoDTO> detalles;

    // Constructores
    public PedidoDTO() {
    }

    public PedidoDTO(Integer idPedido, Integer idUsuario, Integer idSucursal, Integer idMesa,
                     LocalDateTime fechaPedido, String estado, BigDecimal total, String tipoPedido,
                     String nombreCliente, String telefonoCliente) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.idSucursal = idSucursal;
        this.idMesa = idMesa;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.total = total;
        this.tipoPedido = tipoPedido;
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
    }

    // Getters y Setters
    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "PedidoDTO [idPedido=" + idPedido + ", idUsuario=" + idUsuario + ", idSucursal=" + idSucursal
                + ", idMesa=" + idMesa + ", fechaPedido=" + fechaPedido + ", estado=" + estado + ", total=" + total
                + ", tipoPedido=" + tipoPedido + ", nombreCliente=" + nombreCliente + ", telefonoCliente="
                + telefonoCliente + "]";
    }
}
