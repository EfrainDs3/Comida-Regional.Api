package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;
import web.Regional_Api.entity.Pedido;

public interface IPedidoService {
    
    List<Pedido> buscarTodos();
    
    Optional<Pedido> buscarId(Integer id);
    
    Pedido guardar(Pedido pedido);
    
    // No hay 'eliminar' porque usaré 'guardar' (con estado 'Cancelado')
}