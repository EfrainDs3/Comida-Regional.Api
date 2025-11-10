package web.Regional_Api.entity;

public class CategoriaDTO {
    private Integer id_categoria;
    private String nombre;
    private String descripcion;
    private Integer estado;

    public CategoriaDTO() {}

    public CategoriaDTO(Integer id_categoria, String nombre, String descripcion, Integer estado) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
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

    @Override
    public String toString() {
        return "CategoriaDTO [id_categoria=" + id_categoria + ", nombre=" + nombre + "]";
    }
}