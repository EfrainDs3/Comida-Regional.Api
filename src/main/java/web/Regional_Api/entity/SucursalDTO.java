package web.Regional_Api.entity;

public class SucursalDTO {
    private Integer id_sucursal;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ciudad;
    private String estado_sucursal;
    private Integer id_restaurante;
    private Integer estado;

    public SucursalDTO() {}

    public SucursalDTO(Integer id_sucursal, String nombre, String direccion, String telefono, 
                       String ciudad, String estado_sucursal, Integer id_restaurante, Integer estado) {
        this.id_sucursal = id_sucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.estado_sucursal = estado_sucursal;
        this.id_restaurante = id_restaurante;
        this.estado = estado;
    }

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado_sucursal() {
        return estado_sucursal;
    }

    public void setEstado_sucursal(String estado_sucursal) {
        this.estado_sucursal = estado_sucursal;
    }

    public Integer getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(Integer id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "SucursalDTO [id_sucursal=" + id_sucursal + ", nombre=" + nombre + "]";
    }
}
