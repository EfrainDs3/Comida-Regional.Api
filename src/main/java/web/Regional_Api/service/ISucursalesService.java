package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Sucursales;



public interface ISucursalesService {
    
    // READ: Obtener todas las sucursales de un restaurante específico (Multi-tenant)
    List<Sucursales> buscarTodosPorRestaurante(Integer idRestaurante);

    // READ: Obtener una sucursal por ID con validación Multi-tenant
    Optional<Sucursales> buscarIdYRestaurante(Integer idSucursal, Integer idRestaurante);

    // CREATE: Guardar una nueva sucursal (necesita el idRestaurante para asignación)
    void guardar(Sucursales sucursal, Integer idRestaurante);

    // UPDATE: Modificar una sucursal existente (necesita el idRestaurante para validación)
    void modificar(Sucursales sucursalActualizada, Integer idRestaurante);

    // DELETE (Soft Delete): Eliminar lógicamente (necesita el idRestaurante para validación)
    void eliminar(Integer idSucursal, Integer idRestaurante);
}