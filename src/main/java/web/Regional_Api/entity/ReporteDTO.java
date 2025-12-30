package web.Regional_Api.entity;

import java.time.format.DateTimeFormatter;

public class ReporteDTO {
    private Integer idReporte;
    private String accion;
    private String detalle;
    private String ipOrigen;
    private String fecha; // String formateado
    private Integer idSuperAdmin;
    private String nombreSuperAdmin;
    private String rolSuperAdmin;

    public ReporteDTO(Reporte reporte) {
        this.idReporte = reporte.getIdReporte();
        this.accion = reporte.getAccion();
        this.detalle = reporte.getDetalle();
        this.ipOrigen = reporte.getIpOrigen();

        // Formatear fecha manualmente a String para evitar cualquier problema
        if (reporte.getFecha() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.fecha = reporte.getFecha().format(formatter);
        }

        if (reporte.getSuperAdmin() != null) {
            this.idSuperAdmin = reporte.getSuperAdmin().getIdSuperAdmin();
            this.nombreSuperAdmin = reporte.getSuperAdmin().getNombres();
            this.rolSuperAdmin = reporte.getSuperAdmin().getRol();
        }
    }

    // Getters
    public Integer getIdReporte() {
        return idReporte;
    }

    public String getAccion() {
        return accion;
    }

    public String getDetalle() {
        return detalle;
    }

    public String getIpOrigen() {
        return ipOrigen;
    }

    public String getFecha() {
        return fecha;
    }

    public Integer getIdSuperAdmin() {
        return idSuperAdmin;
    }

    public String getNombreSuperAdmin() {
        return nombreSuperAdmin;
    }

    public String getRolSuperAdmin() {
        return rolSuperAdmin;
    }
}
