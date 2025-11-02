package web.Regional_Api.entity;

public class Usuarios {

    private Integer idUsuario;
    private String nombreUsuario;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private String contraseña;
    private Boolean estado;
    private int rolId;

    public Usuarios(Integer idUsuario, String nombreUsuario, String apellidos, String direccion, String email, 
                    String telefono, String contraseña, Boolean estado, int rolId) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.estado = estado;
        this.rolId = rolId;
    }

  
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

   
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    @Override
    public String toString() {
        return "Usuarios [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", apellidos=" + apellidos
                + ", direccion=" + direccion + ", email=" + email + ", telefono=" + telefono + ", contraseña="
                + contraseña + ", estado=" + estado + ", rolId=" + rolId + "]";
    }
}
