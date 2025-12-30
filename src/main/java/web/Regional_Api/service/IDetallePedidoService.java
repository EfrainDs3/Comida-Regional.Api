package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.DetallePedido;

public interface IDetallePedidoService {
    
    // CRUD básico
    List<DetallePedido> buscarTodos();
    
    DetallePedido guardar(DetallePedido detallePedido);
    
    DetallePedido modificar(DetallePedido detallePedido);
    
    Optional<DetallePedido> buscarId(Integer id);
    
    void eliminar(Integer id);
    
    // Búsquedas específicas
    List<DetallePedido> buscarPorPedido(Integer idPedido);
    
    List<DetallePedido> buscarPorPlato(Integer idPlato);
}
