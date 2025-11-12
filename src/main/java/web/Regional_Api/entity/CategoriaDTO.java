package web.Regional_Api.entity;

public class CategoriaDTO {
    private Integer id_categoria;
    
    // --- ¡EL CAMPO QUE FALTABA! ---
    private Integer id_restaurante; 
    
    private String nombre;
    private String descripcion;
    private Integer estado;

    public Integer getId_restaurante() {
        return id_restaurante;
    }
    public void setId_restaurante(Integer id_restaurante) {
        this.id_restaurante = id_restaurante;
    }
    public Integer getId_categoria() {
        return id_categoria;
    }
    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getEstado() {
        return estado;
    }
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}