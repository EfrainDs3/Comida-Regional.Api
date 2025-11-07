package web.Regional_Api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Pedido;

public interface IPedidoService {
    // CRUD para el API de Pedidos
    
    List<Pedido> buscarTodos();
    
    Pedido guardar(Pedido pedido);
    
    Pedido modificar(Pedido pedido);
    
    Optional<Pedido> buscarId(Integer id);
    
    void eliminar(Integer id);
    
    List<Pedido> buscarPorSucursal(Integer idSucursal);
    // Método para obtener pedidos de una sucursal
    
    List<Pedido> buscarPorEstado(String estado);
    // Método para buscar por estado (En preparación, Listo, Entregado)
    
    Optional<Pedido> buscarPorNumeroPedido(String numeroPedido);
    // Método para obtener pedido por número
    
    List<Pedido> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    // Método para obtener pedidos en un rango de fechas
    
    List<Pedido> buscarPorSucursalYEstado(Integer idSucursal, String estado);
    // Método para obtener pedidos de una sucursal con estado específico
    
    List<Pedido> buscarPorSucursalYFechas(Integer idSucursal, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    // Método para obtener pedidos de una sucursal en un rango de fechas
}
