package web.Regional_Api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora_acciones")
public class BitacoraAccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long idLog;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "accion_realizada")
    private String accionRealizada;

    @Column(name = "tabla_afectada")
    private String tablaAfectada;

    @Column(name = "id_registro_afectado")
    private Integer idRegistroAfectado;

    @Column(name = "detalles", columnDefinition = "TEXT")
    private String detalles;

    @Column(name = "fecha_hora")
    private java.sql.Timestamp fechaHora;

    // Constructor vacío
    public BitacoraAccion() {}

    // Constructor para crear logs fácilmente
    public BitacoraAccion(Integer idUsuario, String accionRealizada, String tablaAfectada, 
                          Integer idRegistroAfectado, String detalles) {
        this.idUsuario = idUsuario;
        this.accionRealizada = accionRealizada;
        this.tablaAfectada = tablaAfectada;
        this.idRegistroAfectado = idRegistroAfectado;
        this.detalles = detalles;
       this.fechaHora = new java.sql.Timestamp(System.currentTimeMillis());
    }

    // Getters y Setters
    public Long getIdLog() { return idLog; }
    public void setIdLog(Long idLog) { this.idLog = idLog; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getAccionRealizada() { return accionRealizada; }
    public void setAccionRealizada(String accionRealizada) { this.accionRealizada = accionRealizada; }

    public String getTablaAfectada() { return tablaAfectada; }
    public void setTablaAfectada(String tablaAfectada) { this.tablaAfectada = tablaAfectada; }

    public Integer getIdRegistroAfectado() { return idRegistroAfectado; }
    public void setIdRegistroAfectado(Integer idRegistroAfectado) { this.idRegistroAfectado = idRegistroAfectado; }

    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }

    public java.sql.Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(java.sql.Timestamp fechaHora) { this.fechaHora = fechaHora; }
}