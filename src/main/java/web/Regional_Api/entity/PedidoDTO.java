package web.Regional_Api.entity;

import java.util.List;

public class PedidoDTO {
    
    private Integer id_sucursal;
    private Integer id_mesa;
    private Integer id_usuario_mozo;
    
    private List<DetallePedidoDTO> detalles;

    public Integer getId_sucursal() { return id_sucursal; }
    public void setId_sucursal(Integer id_sucursal) { this.id_sucursal = id_sucursal; }
    public Integer getId_mesa() { return id_mesa; }
    public void setId_mesa(Integer id_mesa) { this.id_mesa = id_mesa; }
    public Integer getId_usuario_mozo() { return id_usuario_mozo; }
    public void setId_usuario_mozo(Integer id_usuario_mozo) { this.id_usuario_mozo = id_usuario_mozo; }
    public List<DetallePedidoDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoDTO> detalles) { this.detalles = detalles; }
}
