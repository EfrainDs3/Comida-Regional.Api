package com.comidas.api.service; 

import java.math.BigDecimal;
import java.util.List;

import com.comidas.api.entity.MovimientosCaja;

public interface IMovimientosCajaService {
    
    // 1. Registrar Ingreso/Egreso (Create)
    void registrarMovimiento(MovimientosCaja movimiento, Integer idUsuarioRegistro);

    // 2. Obtener movimientos de una sesión
    List<MovimientosCaja> buscarPorSesion(Integer idSesion);
    
    // 3. Obtener el total de movimientos de una sesión (útil para el cierre de caja)
    BigDecimal calcularTotalMovimientos(Integer idSesion);
}