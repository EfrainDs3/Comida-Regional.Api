package web.Regional_Api.service; 

import java.math.BigDecimal;
import java.util.List;

import web.Regional_Api.entity.MovimientosCaja;

public interface IMovimientosCajaService {
   
    // 🌟 CREATE: Simplificado. Asume idUsuarioRegistro está en el objeto.
    void registrarMovimiento(MovimientosCaja movimiento);

    // 🌟 READ: Nuevo método para traer todos.
    List<MovimientosCaja> buscarTodos();

    // READ: Buscar por Sesión (existente)
    List<MovimientosCaja> buscarPorSesion(Integer idSesion);
    
    // 🌟 UPDATE: Modificar
    void modificarMovimiento(MovimientosCaja movimientoActualizado);
    
    // 🌟 DELETE: Eliminar por ID.
    void eliminarMovimiento(Integer idMovimiento);
    
    // Método auxiliar (existente)
    BigDecimal calcularTotalMovimientos(Integer idSesion);
}