package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Sucursal;

public interface ISucursalService {
    // CRUD para el API de Sucursales
    
    List<Sucursal> buscarTodos();
    
    Sucursal guardar(Sucursal sucursal);
    
    Sucursal modificar(Sucursal sucursal);
    
    Optional<Sucursal> buscarId(Integer id);
    
    void eliminar(Integer id);
    
    List<Sucursal> buscarPorRestaurante(Integer idRestaurante);
    // Método para obtener todas las sucursales de un restaurante
    
    List<Sucursal> buscarActivasPorRestaurante(Integer idRestaurante);
    // Método para obtener sucursales activas de un restaurante
    
    List<Sucursal> buscarPorEstado(String estado);
    // Método para obtener sucursales por estado
    
    Optional<Sucursal> buscarPorNombreYRestaurante(String nombre, Integer idRestaurante);
    // Método para buscar sucursal por nombre y restaurante
    
    List<Sucursal> buscarPorCiudad(String ciudad);
    // Método para obtener sucursales por ciudad
}

