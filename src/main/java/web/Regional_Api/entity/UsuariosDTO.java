package web.Regional_Api.entity;

import java.time.LocalDateTime;

public class UsuariosDTO {
    private Integer idUsuario;
    private Integer rolId;
    private Integer idSucursal;
    private String nombreUsuario;
    private String apellidos;
    private String dniUsuario;
    private String telefono;
    private String nombreUsuarioLogin;
    private Integer estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimoLogin;
    private String accessToken; // opcional para respuestas después de login/registro

    public UsuariosDTO() {}

    public UsuariosDTO(Integer idUsuario, Integer rolId, Integer idSucursal, String nombreUsuario, String apellidos, String dniUsuario,
                       String telefono, String nombreUsuarioLogin, Integer estado, LocalDateTime fechaCreacion, LocalDateTime ultimoLogin, String accessToken) {
        this.idUsuario = idUsuario;
        this.rolId = rolId;
        this.idSucursal = idSucursal;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.dniUsuario = dniUsuario;
        this.telefono = telefono;
        this.nombreUsuarioLogin = nombreUsuarioLogin;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.ultimoLogin = ultimoLogin;
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

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "UsuariosDTO [idUsuario=" + idUsuario + ", rolId=" + rolId + ", idSucursal=" + idSucursal + ", nombreUsuario=" + nombreUsuario + ", nombreUsuarioLogin=" + nombreUsuarioLogin + "]";
    }
}
