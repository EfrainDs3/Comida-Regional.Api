package web.Regional_Api.entity;

import java.time.LocalDateTime;

/**
 * DTO para SuperAdmin
 * 
 * Representa los datos del SuperAdmin para transferir entre el frontend y el backend.
 */
public class SuperAdminDTO {

    private Integer idSuperadmin;
    private String nombres;
    private String email;
    private String password; // Contraseña encriptada
    private String tokenLogin; // Código temporal enviado al correo
    private LocalDateTime tokenExpiracion; // Hora límite para usar el token
    private String rol; // MASTER, SOPORTE, VENTAS
    private Integer estado; // 1: Activo, 0: Inactivo
    private LocalDateTime fechaCreacion; // Fecha de creación del registro

    // Constructor vacío
    public SuperAdminDTO() {
    }

    // Constructor con parámetros
    public SuperAdminDTO(Integer idSuperadmin, String nombres, String email, String password, String tokenLogin,
                         LocalDateTime tokenExpiracion, String rol, Integer estado, LocalDateTime fechaCreacion) {
        this.idSuperadmin = idSuperadmin;
        this.nombres = nombres;
        this.email = email;
        this.password = password;
        this.tokenLogin = tokenLogin;
        this.tokenExpiracion = tokenExpiracion;
        this.rol = rol;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public Integer getIdSuperadmin() {
        return idSuperadmin;
    }

    public void setIdSuperadmin(Integer idSuperadmin) {
        this.idSuperadmin = idSuperadmin;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenLogin() {
        return tokenLogin;
    }

    public void setTokenLogin(String tokenLogin) {
        this.tokenLogin = tokenLogin;
    }

    public LocalDateTime getTokenExpiracion() {
        return tokenExpiracion;
    }

    public void setTokenExpiracion(LocalDateTime tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    @Override
    public String toString() {
        return "SuperAdminDTO{" +
                "idSuperadmin=" + idSuperadmin +
                ", nombres='" + nombres + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tokenLogin='" + tokenLogin + '\'' +
                ", tokenExpiracion=" + tokenExpiracion +
                ", rol='" + rol + '\'' +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}