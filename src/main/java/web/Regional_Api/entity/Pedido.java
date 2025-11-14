package web.Regional_Api.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
@DynamicInsert
// Fiel a la nueva columna 'estado':
@SQLDelete(sql = "UPDATE pedidos SET estado = 0 WHERE id_pedido = ?")
@Where(clause = "estado = 1")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pedido;

    // --- Relaciones Fieles a FKs (Ahora son NOT NULL) ---
    @ManyToOne
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesas mesa;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false) // Fiel al nuevo nombre
    private Usuarios usuario; // Cambiado de 'usuarioMozo'
    
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "fecha_hora") // Fiel al nuevo nombre
    private LocalDateTime fecha_hora; 
    
    @Column(nullable = false, length = 20) // Fiel al enum
    private String estado_pedido; 
    
    @Column(columnDefinition = "TEXT")
    private String notas; // ¡Nuevo campo!
    
    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer estado = 1; 

    // --- Relación Inversa ---
   @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetallePedido> detalles;

    
    public Integer getId_pedido() { return id_pedido; }
    public void setId_pedido(Integer id_pedido) { this.id_pedido = id_pedido; }
    public Mesas getMesa() { return mesa; }
    public void setMesa(Mesas mesa) { this.mesa = mesa; }
    public Usuarios getUsuario() { return usuario; }
    public void setUsuario(Usuarios usuario) { this.usuario = usuario; }
    public LocalDateTime getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(LocalDateTime fecha_hora) { this.fecha_hora = fecha_hora; }
    public String getEstado_pedido() { return estado_pedido; }
    public void setEstado_pedido(String estado_pedido) { this.estado_pedido = estado_pedido; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
    @Override
    public String toString() {
        return "Pedido [id_pedido=" + id_pedido + ", mesa=" + mesa + ", usuario=" + usuario + ", fecha_hora="
                + fecha_hora + ", estado_pedido=" + estado_pedido + ", notas=" + notas + ", estado=" + estado
                + ", detalles=" + detalles + "]";
    }
    
}