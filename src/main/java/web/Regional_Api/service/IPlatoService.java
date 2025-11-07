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
    
    List<Plato> buscarPorSucursal(Integer idSucursal);
    // Método para obtener todos los platos de una sucursal
    
    List<Plato> buscarDisponiblesPorSucursal(Integer idSucursal);
    // Método para obtener platos disponibles de una sucursal
    
    List<Plato> buscarPorNombre(String nombre);
    // Método para buscar platos por nombre
    
    List<Plato> buscar(String search);
    // Método para búsqueda general
    
    List<Plato> buscarPorDisponibilidad(Integer disponible);
    // Método para filtrar por disponibilidad
}

