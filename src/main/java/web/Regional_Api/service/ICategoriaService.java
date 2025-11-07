package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Categoria;

public interface ICategoriaService {
    // CRUD para el API de Categorías
    
    List<Categoria> buscarTodos();
    
    Categoria guardar(Categoria categoria);
    
    Categoria modificar(Categoria categoria);
    
    Optional<Categoria> buscarId(Integer id);
    
    void eliminar(Integer id);
    
    Optional<Categoria> buscarPorNombre(String nombre);
    // Método para obtener categoría por nombre
    
    List<Categoria> buscarPorNombreContiene(String nombre);
    // Método para buscar categorías que contengan el texto
    
    List<Categoria> buscarActivas();
    // Método para obtener solo categorías activas
}
