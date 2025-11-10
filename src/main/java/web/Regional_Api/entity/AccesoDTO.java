package web.Regional_Api.entity;

public class AccesoDTO {
    private Integer idAcceso;
    private Integer idModulo;
    private Integer idPerfil;
    private Integer estado;

    public AccesoDTO() {}

    public AccesoDTO(Integer idAcceso, Integer idModulo, Integer idPerfil, Integer estado) {
        this.idAcceso = idAcceso;
        this.idModulo = idModulo;
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
        return "AccesoDTO [idAcceso=" + idAcceso + ", idModulo=" + idModulo + ", idPerfil=" + idPerfil + ", estado=" + estado + "]";
    }
}
