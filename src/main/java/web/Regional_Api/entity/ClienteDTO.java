package web.Regional_Api.entity;

import java.time.LocalDateTime;

public class ClienteDTO {
    private Integer idCliente;
    private Integer idRestaurante;
    private String tipoCliente;
    private String nombreRazonSocial;
    private String documento;
    private String email;
    private String telefono;
    private String direccion;
    private Integer estado;
    private LocalDateTime fechaRegistro;

    public ClienteDTO() {}

    public ClienteDTO(Integer idCliente, Integer idRestaurante, String tipoCliente, String nombreRazonSocial,
                      String documento, String email, String telefono, String direccion, Integer estado, LocalDateTime fechaRegistro) {
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
        this.tipoCliente = tipoCliente;
        this.nombreRazonSocial = nombreRazonSocial;
        this.documento = documento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "ClienteDTO [idCliente=" + idCliente + ", idRestaurante=" + idRestaurante + ", tipoCliente=" + tipoCliente + "]";
    }
}
