package web.Regional_Api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Integer idReporte;
    @Column(nullable = false, length = 100)
    private String accion;
    @Column(columnDefinition = "TEXT")
    private String detalle;
    @Column(name = "ip_origen", length = 50)
    private String ipOrigen;

    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime fecha;
    // Relación con SuperAdmin
    // Usamos JsonIgnoreProperties para evitar bucles infinitos al convertir a JSON
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_superadmin", nullable = false)
    @JsonIgnoreProperties({ "password", "tokenLogin", "reportes", "hibernateLazyInitializer", "handler" })
    private SuperAdmin superAdmin;

    // CONSTRUCTORES
    public Reporte() {
        this.fecha = LocalDateTime.now();
    }

    public Reporte(String accion, String detalle, String ipOrigen, SuperAdmin superAdmin) {
        this.accion = accion;
        this.detalle = detalle;
        this.ipOrigen = ipOrigen;
        this.superAdmin = superAdmin;
        this.fecha = LocalDateTime.now();
    }

    // GETTERS Y SETTERS
    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getIpOrigen() {
        return ipOrigen;
    }

    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public SuperAdmin getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(SuperAdmin superAdmin) {
        this.superAdmin = superAdmin;
    }
}