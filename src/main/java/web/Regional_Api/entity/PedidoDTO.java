package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    
    private Integer id_pedido;
    private Integer id_sucursal;
    private Integer id_mesa;
    private Integer id_usuario_mozo;
    private LocalDateTime fecha_hora_pedido;
    private String estado_pedido;
    private BigDecimal total_pedido;

    private List<DetallePedidoDTO> detalles;

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Integer getId_sucursal() { return id_sucursal; }
    public void setId_sucursal(Integer id_sucursal) { this.id_sucursal = id_sucursal; }
    public Integer getId_mesa() { return id_mesa; }
    public void setId_mesa(Integer id_mesa) { this.id_mesa = id_mesa; }
    public Integer getId_usuario_mozo() { return id_usuario_mozo; }
    public void setId_usuario_mozo(Integer id_usuario_mozo) { this.id_usuario_mozo = id_usuario_mozo; }
    public LocalDateTime getFecha_hora_pedido() { return fecha_hora_pedido; }
    public void setFecha_hora_pedido(LocalDateTime fecha_hora_pedido) { this.fecha_hora_pedido = fecha_hora_pedido; }
    public String getEstado_pedido() { return estado_pedido; }
    public void setEstado_pedido(String estado_pedido) { this.estado_pedido = estado_pedido; }
    public BigDecimal getTotal_pedido() { return total_pedido; }
    public void setTotal_pedido(BigDecimal total_pedido) { this.total_pedido = total_pedido; }
    public List<DetallePedidoDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoDTO> detalles) { this.detalles = detalles; }
}
