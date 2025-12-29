package web.Regional_Api.entity;

import java.math.BigDecimal;

public class DetallePedidoDTO {

    private Integer idDetalle;
    private Integer idPedido;
    private Integer idPlato;
    private String nombrePlato;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Constructores
    public DetallePedidoDTO() {
    }

    public DetallePedidoDTO(Integer idPlato, Integer cantidad, BigDecimal precioUnitario) {
        this.idPlato = idPlato;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario.multiply(new BigDecimal(cantidad));
    }

    // Getters y Setters
    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Integer idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetallePedidoDTO [idDetalle=" + idDetalle + ", idPedido=" + idPedido + ", idPlato=" + idPlato
                + ", nombrePlato=" + nombrePlato + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario
                + ", subtotal=" + subtotal + "]";
    }
}
