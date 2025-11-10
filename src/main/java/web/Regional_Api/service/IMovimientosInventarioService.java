package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;
import web.Regional_Api.entity.MovimientosInventario;

public interface IMovimientosInventarioService {
    
    List<MovimientosInventario> buscarTodos();
    Optional<MovimientosInventario> buscarId(Integer id);
    
    // Este método GUARDA el movimiento Y ACTUALIZA el stock del insumo
    MovimientosInventario guardar(MovimientosInventario movimiento);
    
    // NO HAY MODIFICAR NI ELIMINAR para un log de transacciones
}