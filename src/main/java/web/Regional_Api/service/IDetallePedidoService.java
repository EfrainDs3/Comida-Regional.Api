package web.Regional_Api.service;

import java.util.Optional;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.DetallePedidoUpdateDTO; 

public interface IDetallePedidoService {
    
    Optional<DetallePedido> buscarId(Integer id);

    DetallePedido actualizar(Integer idDetalle, DetallePedidoUpdateDTO dto);
    
    void eliminar(Integer idDetalle);
}