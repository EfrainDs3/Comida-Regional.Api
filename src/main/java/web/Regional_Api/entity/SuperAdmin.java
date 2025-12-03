package web.Regional_Api.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
// IMPORTANTE: Ya no necesitamos PasswordEncoder aquí
// import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "super_admins")
@SQLDelete(sql = "UPDATE super_admins SET estado = 0 WHERE id_superadmin = ?")
@Where(clause = "estado = 1")
public class SuperAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_superadmin")
    private Integer idSuperAdmin;

    @Column(name = "nombres", length = 150)
    private String nombres;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    @JsonIgnore // Oculta la contraseña al enviar respuestas JSON
    private String password;

    @Column(name = "token_login")
    private String tokenLogin;

    @Column(name = "token_expiracion")
    private LocalDateTime tokenExpiracion;

    @Column(name = "rol", length = 50)
    private String rol; // MASTER, SOPORTE, VENTAS

    @Column(name = "estado")
    private Integer estado = 1;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // --- HE BORRADO EL CAMPO 'passwordEncoder' Y SU SETTER ESTÁTICO ---

    public SuperAdmin() {
    }

    public SuperAdmin(Integer idSuperAdmin, String nombres, String email, String password, String tokenLogin,
            LocalDateTime tokenExpiracion, String rol, Integer estado, LocalDateTime fechaCreacion) {
        this.idSuperAdmin = idSuperAdmin;
        this.nombres = nombres;
        this.email = email;
        this.password = password;
        this.tokenLogin = tokenLogin;
        this.tokenExpiracion = tokenExpiracion;
        this.rol = rol;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters normales

    public Integer getIdSuperAdmin() {
        return idSuperAdmin;
    }

    public void setIdSuperAdmin(Integer idSuperAdmin) {
        this.idSuperAdmin = idSuperAdmin;
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    // --- ESTA ES LA PARTE CORREGIDA ---
    @JsonProperty // Permite recibir la contraseña en el JSON (Input)
    public void setPassword(String password) {
        // Solo guardamos el valor tal cual llega.
        // La encriptación SE DEBE HACER EN EL SERVICE.
        this.password = password;
    }
    // -----------------------------------

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
        return "SuperAdmin [idSuperAdmin=" + idSuperAdmin + ", nombres=" + nombres + ", email=" + email + ", rol=" + rol
                + ", estado=" + estado + "]";
    }
}