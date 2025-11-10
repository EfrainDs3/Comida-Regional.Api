package web.Regional_Api.entity;

public class UsuariosDTO {
    private Integer idUsuario;
    private Integer rolId;
    private String nombreUsuario;
    private String apellidos;
    private String dniUsuario;
    private String telefono;
    private String nombreUsuarioLogin;
    private Integer estado;
    private String accessToken; // opcional para respuestas después de login/registro

    public UsuariosDTO() {}

    public UsuariosDTO(Integer idUsuario, Integer rolId, String nombreUsuario, String apellidos, String dniUsuario,
                       String telefono, String nombreUsuarioLogin, Integer estado, String accessToken) {
        this.idUsuario = idUsuario;
        this.rolId = rolId;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.dniUsuario = dniUsuario;
        this.telefono = telefono;
        this.nombreUsuarioLogin = nombreUsuarioLogin;
        this.estado = estado;
        this.accessToken = accessToken;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
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

    public String getDniUsuario() {
        return dniUsuario;
    }

    public void setDniUsuario(String dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreUsuarioLogin() {
        return nombreUsuarioLogin;
    }

    public void setNombreUsuarioLogin(String nombreUsuarioLogin) {
        this.nombreUsuarioLogin = nombreUsuarioLogin;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "UsuariosDTO [idUsuario=" + idUsuario + ", rolId=" + rolId + ", nombreUsuario=" + nombreUsuario + ", nombreUsuarioLogin=" + nombreUsuarioLogin + "]";
    }
}
