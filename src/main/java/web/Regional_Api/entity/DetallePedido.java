package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_pedido")
@SQLDelete(sql = "UPDATE detalle_pedido SET estado = 0 WHERE id_detalle = ?")
@Where(clause = "estado = 1")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;
    
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private String observaciones;
    private String opcionesJSON; // JSON con opciones adicionales seleccionadas
    private Integer estado = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plato")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Plato plato;


    public DetallePedido() {}

    public DetallePedido(Integer id) {
        this.idDetalle = id;
    }

    // Getters and Setters
    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getOpcionesJSON() {
        return opcionesJSON;
    }

    public void setOpcionesJSON(String opcionesJSON) {
        this.opcionesJSON = opcionesJSON;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }


    @Override
    public String toString() {
        return "DetallePedido [idDetalle=" + idDetalle + ", cantidad=" + cantidad + 
               ", precioUnitario=" + precioUnitario + ", estado=" + estado + "]";
    }
}

