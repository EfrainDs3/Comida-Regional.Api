package web.Regional_Api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Pedido;

public interface IPedidoService {
    
    List<Pedido> buscarTodos();
    
    Pedido guardar(Pedido pedido);
    
    Pedido modificar(Pedido pedido);
    
    Optional<Pedido> buscarId(Integer id);
    
    void eliminar(Integer id);
    
    List<Pedido> buscarPorSucursal(Integer idSucursal);
    
    List<Pedido> buscarPorEstado(Integer estado);
    
    List<Pedido> buscarPorUsuario(Integer idUsuario);
    
    List<Pedido> buscarPorMesa(Integer idMesa);
    
    List<Pedido> buscarPorPlato(Integer idPlato);
    
    List<Pedido> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    List<Pedido> buscarPorSucursalYEstado(Integer idSucursal, Integer estado);
    
    List<Pedido> buscarPorTipoPedido(String tipoPedido);
    
    List<Pedido> buscarPedidosActivos(Integer idSucursal);
}
