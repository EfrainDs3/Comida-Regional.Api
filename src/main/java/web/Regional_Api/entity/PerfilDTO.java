package web.Regional_Api.entity;

public class PerfilDTO {
    private Integer idPerfil;
    private String nombrePerfil;
    private Integer estado;

    public PerfilDTO() {}

    public PerfilDTO(Integer idPerfil, String nombrePerfil, Integer estado) {
        this.idPerfil = idPerfil;
        this.nombrePerfil = nombrePerfil;
        this.estado = estado;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PerfilDTO [idPerfil=" + idPerfil + ", nombrePerfil=" + nombrePerfil + ", estado=" + estado + "]";
    }
}
