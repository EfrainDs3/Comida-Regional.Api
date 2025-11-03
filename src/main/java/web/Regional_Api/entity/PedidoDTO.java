package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoDTO {
    private Integer id_pedido;
    private String numero_pedido;
    private LocalDateTime fecha_pedido;
    private String estado_pedido;
    private BigDecimal total;
    private String mesa_numero;
    private String notas;
    private Integer id_sucursal;
    private Integer estado;

    public PedidoDTO() {}

    public PedidoDTO(Integer id_pedido, String numero_pedido, LocalDateTime fecha_pedido, String estado_pedido,
                     BigDecimal total, String mesa_numero, String notas, Integer id_sucursal, Integer estado) {
        this.id_pedido = id_pedido;
        this.numero_pedido = numero_pedido;
        this.fecha_pedido = fecha_pedido;
        this.estado_pedido = estado_pedido;
        this.total = total;
        this.mesa_numero = mesa_numero;
        this.notas = notas;
        this.id_sucursal = id_sucursal;
        this.estado = estado;
    }

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getNumero_pedido() {
        return numero_pedido;
    }

    public void setNumero_pedido(String numero_pedido) {
        this.numero_pedido = numero_pedido;
    }

    public LocalDateTime getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(LocalDateTime fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getEstado_pedido() {
        return estado_pedido;
    }

    public void setEstado_pedido(String estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getMesa_numero() {
        return mesa_numero;
    }

    public void setMesa_numero(String mesa_numero) {
        this.mesa_numero = mesa_numero;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PedidoDTO [id_pedido=" + id_pedido + ", numero_pedido=" + numero_pedido + ", estado_pedido="
                + estado_pedido + "]";
    }
}
