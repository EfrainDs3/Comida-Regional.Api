package web.Regional_Api.entity;

public class RolUsuario {

    private int idRol;
    private String rolNombre;

    
    public int getIdRol() {
        return idRol;
    }
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
    public String getRolNombre() {
        return rolNombre;
    }
    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }


    @Override
    public String toString() {
        return "RolUsuario [idRol=" + idRol + ", rolNombre=" + rolNombre + "]";
    }

}
