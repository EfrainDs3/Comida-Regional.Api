package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Plato;

public interface IPlatoService {
    // CRUD para el API de Platos
    
    List<Plato> buscarTodos();
    
    Plato guardar(Plato plato);
    
    Plato modificar(Plato plato);
    
    Optional<Plato> buscarId(Integer id);
    
    void eliminar(Integer id);
    
    List<Plato> buscarPorCategoria(Integer idCategoria);
    // Método para obtener platos de una categoría
    
    List<Plato> buscarDisponiblesPorCategoria(Integer idCategoria);
    // Método para obtener platos disponibles (no desactivados)
    
    // NOTE: The Plato entity has no sucursal relation in the current model,
    // so sucursal-related service methods were removed.
    
    List<Plato> buscarPorNombre(String nombre);
    // Método para buscar platos por nombre
    
    List<Plato> buscar(String search);
    // Método para búsqueda general
    
    List<Plato> buscarPorDisponibilidad(Integer disponible);
    // Método para filtrar por disponibilidad
}