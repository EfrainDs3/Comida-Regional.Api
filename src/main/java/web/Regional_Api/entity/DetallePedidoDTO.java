package web.Regional_Api.entity;

// DTO simple solo para actualizar los campos permitidos

import java.math.BigDecimal;

public class DetallePedidoDTO {
    
    private Integer id_plato;
    private Integer cantidad;
    private String observaciones;
    private BigDecimal precio_unitario;
    
    public Integer getId_plato() {
        return id_plato;
    }
    public BigDecimal getPrecio_unitario() {
        return precio_unitario;
    }
    public void setPrecio_unitario(BigDecimal precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    public void setId_plato(Integer id_plato) {
        this.id_plato = id_plato;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}