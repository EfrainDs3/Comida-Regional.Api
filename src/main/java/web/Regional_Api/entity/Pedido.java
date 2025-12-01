package web.Regional_Api.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
@DynamicInsert
@SQLDelete(sql = "UPDATE pedidos SET estado = 0 WHERE id_pedido = ?")
@Where(clause = "estado = 1")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pedido;

    // CAMBIO CLAVE: Usamos Integer (ID) en lugar de la Entidad completa
    @Column(name = "id_mesa", nullable = false)
    private Integer id_mesa;

    // CAMBIO CLAVE: Usamos Integer (ID) en lugar de la Entidad completa
    @Column(name = "id_usuario", nullable = false)
    private Integer id_usuario;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "fecha_hora")
    private LocalDateTime fecha_hora; 
    
    @Column(nullable = false, length = 20)
    private String estado_pedido; 
    
    @Column(columnDefinition = "TEXT")
    private String notas;
    
    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer estado = 1; 
    
    public Integer getId_pedido() { return id_pedido; }
    public void setId_pedido(Integer id_pedido) { this.id_pedido = id_pedido; }
    
    public Integer getId_mesa() { return id_mesa; }
    public void setId_mesa(Integer id_mesa) { this.id_mesa = id_mesa; }
    
    public Integer getId_usuario() { return id_usuario; }
    public void setId_usuario(Integer id_usuario) { this.id_usuario = id_usuario; }
    
    public LocalDateTime getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(LocalDateTime fecha_hora) { this.fecha_hora = fecha_hora; }
    
    public String getEstado_pedido() { return estado_pedido; }
    public void setEstado_pedido(String estado_pedido) { this.estado_pedido = estado_pedido; }
    
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
    @Override
    public String toString() {
        return "Pedido [id_pedido=" + id_pedido + ", id_mesa=" + id_mesa + ", id_usuario=" + id_usuario
                + ", fecha_hora=" + fecha_hora + ", estado_pedido=" + estado_pedido + ", notas=" + notas + ", estado="
                + estado + "]";
    }

    
}