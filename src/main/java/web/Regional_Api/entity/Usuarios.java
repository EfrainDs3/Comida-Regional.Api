package web.Regional_Api.entity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "usuario")
@SQLDelete(sql = "UPDATE usuario SET estado = 0 WHERE id_usuario = ?")
@Where(clause = "estado = 1")

public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_perfil")
    private Integer rolId; // mapeado a id_perfil en la nueva tabla

    @Column(name = "id_sucursal")
    private Integer idSucursal; // nullable en la BD

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "apellido_usuario")
    private String apellidos;

    @Column(name = "dni_usuario")
    private String dniUsuario;

    @Column(name = "telefono_usuario")
    private String telefono;

    @Column(name = "nombre_usuario_login")
    private String nombreUsuarioLogin;

    @Column(name = "contraseña_usuario")
    private String contraseña; 

    @Column(name = "estado")
    private Integer estado = 1;

 
    @Transient
    private String accessToken;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    public Usuarios(){
    }
    
    public Usuarios(Integer idUsuario, String nombreUsuario, String apellidos, String dniUsuario, String telefono,
            String contraseña, Integer estado, int rolId, String nombreUsuarioLogin, String accessToken,
            Integer idSucursal, LocalDateTime fechaCreacion, LocalDateTime ultimoLogin) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.dniUsuario = dniUsuario;
        this.telefono = telefono;
        this.setContraseña(contraseña);
        this.accessToken = accessToken;
        this.estado = estado;
        this.rolId = rolId;
        this.nombreUsuarioLogin = nombreUsuarioLogin;
        this.idSucursal = idSucursal;
        this.fechaCreacion = fechaCreacion;
        this.ultimoLogin = ultimoLogin;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        if (contraseña != null && !contraseña.isEmpty()) {
            try{
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(contraseña.getBytes());
                byte[] digest = md.digest();
                String result = new BigInteger(1, digest).toString(16).toUpperCase();

                while (result.length() < 64) {
                    result = "0" + result;
                }

                this.contraseña = result;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error al encriptar la contraseña", e);
            }
        }
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }


    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
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

    public String getNombreUsuarioLogin() {
        return nombreUsuarioLogin;
    }

    public void setNombreUsuarioLogin(String nombreUsuarioLogin) {
        this.nombreUsuarioLogin = nombreUsuarioLogin;
    }

    @Override
    public String toString() {
    return "Usuarios [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", apellidos=" + apellidos
        + ", dniUsuario=" + dniUsuario + ", telefono=" + telefono + ", contraseña="
        + contraseña + ", estado=" + estado + ", rolId=" + rolId + ", nombreUsuarioLogin=" + nombreUsuarioLogin + ", accessToken=" + accessToken + "]";
    }
    
}
