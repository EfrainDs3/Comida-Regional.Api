package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Sucursales;

public interface ISucursalesService {
    
    // READ: Obtener todas las sucursales de un restaurante específico (Multi-tenant)
    List<Sucursales> buscarTodosPorRestaurante(Integer idRestaurante);

    // READ: Obtener una sucursal por ID con validación Multi-tenant
    Optional<Sucursales> buscarIdYRestaurante(Integer idSucursal, Integer idRestaurante);

    Optional<Sucursales> buscarId(Integer id);

    // 🌟 NUEVO: Obtener todas las sucursales sin filtro (para pruebas)
    List<Sucursales> buscarTodos(); 

    // 🌟 CREATE: Guardar una nueva sucursal (ya no asigna idRestaurante)
    void guardar(Sucursales sucursal);

    // 🌟 UPDATE: Modificar una sucursal existente (sin validación Multi-tenant)
    void modificar(Sucursales sucursalActualizada);

    // 🌟 DELETE (Soft Delete): Eliminar lógicamente (sin validación Multi-tenant)
    void eliminar(Integer idSucursal);
}