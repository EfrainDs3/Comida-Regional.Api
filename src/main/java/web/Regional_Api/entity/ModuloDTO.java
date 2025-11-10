package web.Regional_Api.entity;

public class ModuloDTO {
    private Integer idModulo;
    private String nombreModulo;
    private Integer orden;
    private Integer estado;

    public ModuloDTO() {}

    public ModuloDTO(Integer idModulo, String nombreModulo, Integer orden, Integer estado) {
        this.idModulo = idModulo;
        this.nombreModulo = nombreModulo;
        this.orden = orden;
        this.estado = estado;
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ModuloDTO [idModulo=" + idModulo + ", nombreModulo=" + nombreModulo + ", orden=" + orden + ", estado=" + estado + "]";
    }
}
