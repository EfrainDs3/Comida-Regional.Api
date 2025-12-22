package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    
    // IDs de las FK (ahora sin sucursal)
    private Integer id_mesa;
    private Integer id_usuario; // Fiel al nuevo nombre
    
    private String notas; // Nuevo campo
    private LocalDateTime fecha_hora; 
    private String estado_pedido; 
    private Integer estado = 1;
    private BigDecimal total;
    private String jsonDetalles;

    // Lista de "hijos"
    private List<DetallePedidoDTO> detalles;

    // --- Getters y Setters ---
    
    public LocalDateTime getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(LocalDateTime fecha_hora) { this.fecha_hora
    = fecha_hora; }
    public String getEstado_pedido() { return estado_pedido; }
    public void setEstado_pedido(String estado_pedido) { this.estado_pedido = estado_pedido; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getJsonDetalles() { return jsonDetalles; }
    public void setJsonDetalles(String jsonDetalles) { this.jsonDetalles = jsonDetalles; }
    public Integer getId_mesa() { return id_mesa; }
    public void setId_mesa(Integer id_mesa) { this.id_mesa = id_mesa; }
    public Integer getId_usuario() { return id_usuario; }
    public void setId_usuario(Integer id_usuario) { this.id_usuario = id_usuario; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public List<DetallePedidoDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoDTO> detalles) { this.detalles = detalles; }
}