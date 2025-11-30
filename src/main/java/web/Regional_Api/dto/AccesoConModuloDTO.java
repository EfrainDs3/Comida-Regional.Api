package web.Regional_Api.dto;

public class AccesoConModuloDTO {

    private Integer idAcceso;
    private Integer idModulo;
    private String nombreModulo;
    private Integer orden;
    private Integer idPerfil;
    private Integer estado;

    public AccesoConModuloDTO() {
    }

    public AccesoConModuloDTO(Integer idAcceso, Integer idModulo, String nombreModulo,
            Integer orden, Integer idPerfil, Integer estado) {
        this.idAcceso = idAcceso;
        this.idModulo = idModulo;
        this.nombreModulo = nombreModulo;
        this.orden = orden;
        this.idPerfil = idPerfil;
        this.estado = estado;
    }

    public Integer getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(Integer idAcceso) {
        this.idAcceso = idAcceso;
    }

    public Integer getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "AccesoConModuloDTO [idAcceso=" + idAcceso + ", idModulo=" + idModulo +
                ", nombreModulo=" + nombreModulo + ", orden=" + orden + ", idPerfil=" +
                idPerfil + ", estado=" + estado + "]";
    }
}
