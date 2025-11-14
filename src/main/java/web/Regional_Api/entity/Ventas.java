package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
@SQLDelete(sql = "UPDATE ventas SET estado=0 WHERE id_venta=?") 
@Where(clause = "estado=1") 
public class Ventas {
    
    // Mapeo de ENUMs
    public enum TipoComprobante {
        Boleta, Factura, Nota_de_Venta // Usaremos guiones bajos para espacios si es necesario
    }
    
    public enum MetodoPago {
        Efectivo, Tarjeta, Billetera_Digital, Mixto // Usaremos guiones bajos
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;

    @Column(name = "id_sesion", nullable = false)
    private Integer idSesion;

    @Column(name = "id_cliente") // Puede ser NULL si la venta es anónima
    private Integer idCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_comprobante", nullable = false)
    private TipoComprobante tipoComprobante;

    @Column(name = "serie_comprobante", length = 5)
    private String serieComprobante;

    @Column(name = "numero_comprobante")
    private Integer numeroComprobante;

    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal impuestos;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta = LocalDateTime.now();

    @Column(nullable = false)
    private Integer estado = 1;

    public Ventas() {}

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }

    public Integer getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(Integer numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ventas [idVenta=" + idVenta + ", idPedido=" + idPedido + ", idSesion=" + idSesion + ", idCliente="
                + idCliente + ", tipoComprobante=" + tipoComprobante + ", serieComprobante=" + serieComprobante
                + ", numeroComprobante=" + numeroComprobante + ", montoTotal=" + montoTotal + ", impuestos=" + impuestos
                + ", metodoPago=" + metodoPago + ", fechaVenta=" + fechaVenta + ", estado=" + estado + "]";
    }
}