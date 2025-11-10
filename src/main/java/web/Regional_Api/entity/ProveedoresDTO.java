package web.Regional_Api.entity; // Te recomiendo crear un paquete 'dto'


public class ProveedoresDTO {

    // Campos de la entidad que quieres exponer
    private Integer id_proveedor;
    private String nombre;
    private String ruc;
    private String contacto_nombre;
    private String contacto_telefono;
    private Integer estado;

    // --- CLAVE ---
    // Para la relación @ManyToOne, el DTO solo debe pedir el ID
    // del restaurante al que se va a asociar.
    private Integer idRestaurante;

    // Constructor vacío
    public ProveedoresDTO() {
    }

    // --- Getters y Setters para todos los campos ---
    
    public Integer getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Integer id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getContacto_nombre() {
        return contacto_nombre;
    }

    public void setContacto_nombre(String contacto_nombre) {
        this.contacto_nombre = contacto_nombre;
    }

    public String getContacto_telefono() {
        return contacto_telefono;
    }

    public void setContacto_telefono(String contacto_telefono) {
        this.contacto_telefono = contacto_telefono;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
}