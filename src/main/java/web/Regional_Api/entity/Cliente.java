package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
@SQLDelete(sql = "UPDATE clientes SET estado = false WHERE id_cliente=?")
@Where(clause = "estado = 1")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idCliente;
    private String tipoCliente;
    private String documentoTipo;
    private String documentoNumero;
    private String nombres;
    private String apellidos;
    private String razonSocial;
    private String telefono;
    private String email;
    private String direccion;
    
    public Integer getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    public String getTipoCliente() {
        return tipoCliente;
    }
    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    public String getDocumentoTipo() {
        return documentoTipo;
    }
    public void setDocumentoTipo(String documentoTipo) {
        this.documentoTipo = documentoTipo;
    }
    public String getDocumentoNumero() {
        return documentoNumero;
    }
    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Cliente [idCliente=" + idCliente + ", tipoCliente=" + tipoCliente + ", documentoTipo=" + documentoTipo
                + ", documentoNumero=" + documentoNumero + ", nombres=" + nombres + ", apellidos=" + apellidos
                + ", razonSocial=" + razonSocial + ", telefono=" + telefono + ", email=" + email + ", direccion="
                + direccion + "]";
    }

}

//hola 
