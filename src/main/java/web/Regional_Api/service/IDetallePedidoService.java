package web.Regional_Api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.DetallePedido;

public interface IDetallePedidoService {
    // CRUD para el API de Detalles de Pedido
    
    List<DetallePedido> buscarTodos();
    
    DetallePedido guardar(DetallePedido detallePedido);
    
    DetallePedido modificar(DetallePedido detallePedido);
    
    Optional<DetallePedido> buscarId(Integer id);
    
    void eliminar(Integer id);
    
    List<DetallePedido> buscarPorPedido(Integer idPedido);
    // Método para obtener detalles de un pedido
    
    List<DetallePedido> buscarPorPlato(Integer idPlato);
    // Método para obtener detalles que contengan un plato
    
    BigDecimal calcularTotalPedido(Integer idPedido);
    // Método para calcular el total de un pedido
}
