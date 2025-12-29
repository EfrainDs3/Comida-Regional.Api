package web.Regional_Api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Pedido;

public interface IPedidoService {
    // CRUD básico
    List<Pedido> buscarTodos();
    
    Pedido guardar(Pedido pedido);
    
    Pedido modificar(Pedido pedido);
    
    Optional<Pedido> buscarId(Integer id);
    
    void eliminar(Integer id);
    
    // Búsquedas específicas
    List<Pedido> buscarPorSucursal(Integer idSucursal);
    
    List<Pedido> buscarPorEstado(String estado);
    
    List<Pedido> buscarPorUsuario(Integer idUsuario);
    
    List<Pedido> buscarPorMesa(Integer idMesa);
    
    List<Pedido> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    List<Pedido> buscarPorSucursalYEstado(Integer idSucursal, String estado);
    
    // Cálculo de total
    Double calcularTotal(Integer idPedido);
}
