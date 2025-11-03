package web.Regional_Api.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_pedido")
@SQLDelete(sql = "UPDATE detalles_pedido SET estado = 0 WHERE id_detalle = ?")
@Where(clause = "estado = 1")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_detalle;
    
    private Integer cantidad;
    private BigDecimal precio_unitario;
    private BigDecimal subtotal;
    private String observaciones;
    private Integer estado = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pedido id_pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plato")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Plato id_plato;

    public DetallePedido() {}

    public DetallePedido(Integer id) {
        this.id_detalle = id;
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Pedido getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Pedido id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Plato getId_plato() {
        return id_plato;
    }

    public void setId_plato(Plato id_plato) {
        this.id_plato = id_plato;
    }

    @Override
    public String toString() {
        return "DetallePedido [id_detalle=" + id_detalle + ", cantidad=" + cantidad + ", subtotal=" + subtotal
                + ", estado=" + estado + "]";
    }
}
