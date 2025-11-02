package web.Regional_Api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idPedido;
    private Integer idSucursal;
    private String numeroPedido;
    private String estado;
    private BigDecimal total;
    private String observaciones;
    private String tipoPedido;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructores
    public PedidoDTO() {}

    public PedidoDTO(Integer idPedido, Integer idSucursal, String numeroPedido,
                    String estado, BigDecimal total, String tipoPedido) {
        this.idPedido = idPedido;
        this.idSucursal = idSucursal;
        this.numeroPedido = numeroPedido;
        this.estado = estado;
        this.total = total;
        this.tipoPedido = tipoPedido;
    }

    // Getters y Setters
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

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "PedidoDTO [idPedido=" + idPedido + ", idSucursal=" + idSucursal
                + ", numeroPedido=" + numeroPedido + ", estado=" + estado + ", total=" + total + "]";
    }
}
