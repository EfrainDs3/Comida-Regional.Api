package web.Regional_Api.entity;

import java.math.BigDecimal;

public class DetallePedidoDTO {
    private Integer id_detalle;
    private Integer cantidad;
    private BigDecimal precio_unitario;
    private BigDecimal subtotal;
    private String observaciones;
    private Integer id_pedido;
    private Integer id_plato;
    private Integer estado;

    public DetallePedidoDTO() {}

    public DetallePedidoDTO(Integer id_detalle, Integer cantidad, BigDecimal precio_unitario, BigDecimal subtotal,
                            String observaciones, Integer id_pedido, Integer id_plato, Integer estado) {
        this.id_detalle = id_detalle;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
        this.observaciones = observaciones;
        this.id_pedido = id_pedido;
        this.id_plato = id_plato;
        this.estado = estado;
    }

    public Integer getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(Integer id_detalle) {
        this.id_detalle = id_detalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(BigDecimal precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Integer getId_plato() {
        return id_plato;
    }

    public void setId_plato(Integer id_plato) {
        this.id_plato = id_plato;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "DetallePedidoDTO [id_detalle=" + id_detalle + ", cantidad=" + cantidad + ", subtotal=" + subtotal + "]";
    }
}
