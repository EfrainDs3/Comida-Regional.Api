package web.Regional_Api.entity;

public class Permisos {

    private Integer idPermiso;  
    private Integer rolUsuarioId; 
    private Integer opcionId; 
    private Boolean permitido; 


    public Permisos(Integer idPermiso, Integer rolUsuarioId, Integer opcionId, Boolean permitido) {
        this.idPermiso = idPermiso;
        this.rolUsuarioId = rolUsuarioId;
        this.opcionId = opcionId;
        this.permitido = permitido;
    }

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

 
    public Integer getRolUsuarioId() {
        return rolUsuarioId;
    }

    public void setRolUsuarioId(Integer rolUsuarioId) {
        this.rolUsuarioId = rolUsuarioId;
    }

    
    public Integer getOpcionId() {
        return opcionId;
    }

    public void setOpcionId(Integer opcionId) {
        this.opcionId = opcionId;
    }

    // Getter y Setter para permitido
    public Boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(Boolean permitido) {
        this.permitido = permitido;
    }
}
