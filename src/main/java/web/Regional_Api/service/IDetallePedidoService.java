package web.Regional_Api.service;

import java.util.Optional;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.DetallePedidoDTO;

public interface IDetallePedidoService {
    
    Optional<DetallePedido> buscarId(Integer id);
    
    // Recibe el ID del detalle y el DTO con los nuevos datos
    DetallePedido actualizar(Integer idDetalle, DetallePedidoDTO dto);
    
    // Elimina el detalle y recalcula el total del padre
    void eliminar(Integer idDetalle);
}