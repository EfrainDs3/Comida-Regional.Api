package web.Regional_Api.entity;

import java.util.List;

public class PedidoDTO {
    
    // IDs de las FK (ahora sin sucursal)
    private Integer id_mesa;
    private Integer id_usuario; // Fiel al nuevo nombre
    
    private String notas; // Nuevo campo
    
    // Lista de "hijos"
    private List<DetallePedidoDTO> detalles;

    // --- Getters y Setters ---
    
    public Integer getId_mesa() { return id_mesa; }
    public void setId_mesa(Integer id_mesa) { this.id_mesa = id_mesa; }
    public Integer getId_usuario() { return id_usuario; }
    public void setId_usuario(Integer id_usuario) { this.id_usuario = id_usuario; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public List<DetallePedidoDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoDTO> detalles) { this.detalles = detalles; }
}