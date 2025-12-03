package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*; // Importamos todo para abreviar
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Entity
@Table(name = "ventas")
@SQLDelete(sql = "UPDATE ventas SET estado=0 WHERE id_venta=?") 
@Where(clause = "estado=1") 
public class Ventas {
    
    // --- 1. ENUM TIPO COMPROBANTE CON TRADUCCIÓN ---
    public enum TipoComprobante {
        Boleta("Boleta"), 
        Factura("Factura"), 
        Nota_de_Venta("Nota de Venta"); // Aquí definimos cómo se llama en la BD

        private final String nombreDb;

        TipoComprobante(String nombreDb) {
            this.nombreDb = nombreDb;
        }

        public String getNombreDb() {
            return nombreDb;
        }
        
        // Método para buscar el Enum basándose en el texto de la BD
        public static TipoComprobante of(String nombreDb) {
            return Stream.of(TipoComprobante.values())
                .filter(p -> p.getNombreDb().equals(nombreDb))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo Comprobante desconocido: " + nombreDb));
        }
    }
    
    // --- 2. ENUM METODO PAGO CON TRADUCCIÓN ---
    public enum MetodoPago {
        Efectivo("Efectivo"), 
        Tarjeta("Tarjeta"), 
        Billetera_Digital("Billetera Digital"), // El texto exacto de la BD
        Mixto("Mixto");

        private final String nombreDb;

        MetodoPago(String nombreDb) {
            this.nombreDb = nombreDb;
        }

        public String getNombreDb() {
            return nombreDb;
        }

        public static MetodoPago of(String nombreDb) {
            return Stream.of(MetodoPago.values())
                .filter(p -> p.getNombreDb().equals(nombreDb))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Metodo Pago desconocido: " + nombreDb));
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;

    @Column(name = "id_sesion", nullable = false)
    private Integer idSesion;

    @Column(name = "id_cliente")
    private Integer idCliente;

    // --- CAMBIO IMPORTANTE AQUÍ: Usamos el Converter ---
    @Convert(converter = TipoComprobanteConverter.class)
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

    // --- CAMBIO IMPORTANTE AQUÍ: Usamos el Converter ---
    @Convert(converter = MetodoPagoConverter.class)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta = LocalDateTime.now();

    @Column(nullable = false)
    private Integer estado = 1;

    public Ventas() {}

    // Getters y Setters (Igual que antes)
    public Integer getIdVenta() { return idVenta; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }
    public Integer getIdPedido() { return idPedido; }
    public void setIdPedido(Integer idPedido) { this.idPedido = idPedido; }
    public Integer getIdSesion() { return idSesion; }
    public void setIdSesion(Integer idSesion) { this.idSesion = idSesion; }
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    public TipoComprobante getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(TipoComprobante tipoComprobante) { this.tipoComprobante = tipoComprobante; }
    public String getSerieComprobante() { return serieComprobante; }
    public void setSerieComprobante(String serieComprobante) { this.serieComprobante = serieComprobante; }
    public Integer getNumeroComprobante() { return numeroComprobante; }
    public void setNumeroComprobante(Integer numeroComprobante) { this.numeroComprobante = numeroComprobante; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public BigDecimal getImpuestos() { return impuestos; }
    public void setImpuestos(BigDecimal impuestos) { this.impuestos = impuestos; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }

    // --- CLASES CONVERTIDORAS (TRADUCTORES) ---
    // Estas clases le enseñan a JPA cómo leer los espacios
    
    @Converter(autoApply = true)
    public static class TipoComprobanteConverter implements AttributeConverter<TipoComprobante, String> {
        @Override
        public String convertToDatabaseColumn(TipoComprobante attribute) {
            if (attribute == null) return null;
            return attribute.getNombreDb();
        }

        @Override
        public TipoComprobante convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return TipoComprobante.of(dbData);
        }
    }

    @Converter(autoApply = true)
    public static class MetodoPagoConverter implements AttributeConverter<MetodoPago, String> {
        @Override
        public String convertToDatabaseColumn(MetodoPago attribute) {
            if (attribute == null) return null;
            return attribute.getNombreDb();
        }

        @Override
        public MetodoPago convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return MetodoPago.of(dbData);
        }
    }
}