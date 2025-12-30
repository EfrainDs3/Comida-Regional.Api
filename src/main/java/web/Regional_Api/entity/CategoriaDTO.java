package web.Regional_Api.entity;

public class CategoriaDTO {
    private Integer id_categoria;
    private Integer id_sucursal;
    private String nombre;
    private String descripcion;
    private Integer estado;

    public Integer getId_categoria() {
        return id_categoria;
    }
    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
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