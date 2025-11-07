package web.Regional_Api.entity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
@SQLDelete(sql = "UPDATE usuarios SET estado = false WHERE id_usuario = ?")
@Where(clause = "estado = true")

public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;

    @Column(name = "contraseña")
    private String contraseña;
    
    private Boolean estado = true;

    @Column(name = "rol_id")
    private int rolId;

    @Column(name = "access_token", length = 500)
    private String accessToken;

    public Usuarios(){
    }
    
    public Usuarios(Integer idUsuario, String nombreUsuario, String apellidos, String direccion, String email,
            String telefono, String contraseña, Boolean estado, int rolId, String accessToken) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.setContraseña(contraseña);
        this.accessToken = accessToken;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "Usuarios [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", apellidos=" + apellidos
                + ", direccion=" + direccion + ", email=" + email + ", telefono=" + telefono + ", contraseña="
                + contraseña + ", estado=" + estado + ", rolId=" + rolId + ", accessToken=" + accessToken + "]";
    }
    
}
