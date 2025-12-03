package web.Regional_Api.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
    @JsonIgnore
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

    @JsonProperty
    public void setPassword(String password) {
        System.out.println(">>> setPassword called with: '" + password + "' (length: "
                + (password != null ? password.length() : 0) + ")");

        if (password == null || password.isEmpty()) {
            return;
        }

        // Si la contraseña ya está encriptada (64 caracteres hexadecimales = SHA-256),
        // no re-encriptar
        if (password.matches("^[0-9A-Fa-f]{64}$")) {
            System.out.println(">>> Password is already a hash, storing as-is");
            this.password = password.toUpperCase();
            return;
        }

        // Encriptar la contraseña con SHA-256
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String result = new java.math.BigInteger(1, digest).toString(16).toUpperCase();

            while (result.length() < 64) {
                result = "0" + result;
            }

            System.out.println(">>> Hashed '" + password + "' to: " + result);
            this.password = result;
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
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
        return "SuperAdmin [idSuperAdmin=" + idSuperAdmin + ", nombres=" + nombres + ", email=" + email + ", rol=" + rol
                + ", estado=" + estado + "]";
    }
}
