package web.Regional_Api.service; 

import java.math.BigDecimal;
import java.util.List;

import web.Regional_Api.entity.MovimientosCaja;



public interface IMovimientosCajaService {
   
    void registrarMovimiento(MovimientosCaja movimiento, Integer idUsuarioRegistro);

    
    List<MovimientosCaja> buscarPorSesion(Integer idSesion);
    
    BigDecimal calcularTotalMovimientos(Integer idSesion);
}