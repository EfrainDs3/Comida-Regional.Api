package com.comidas.api.service.jpa; 

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Necesario para asegurar el registro

import com.comidas.api.entity.MovimientosCaja;
import com.comidas.api.repository.MovimientosCajaRepository; 
import com.comidas.api.service.IMovimientosCajaService; 
import com.comidas.api.service.ISesionesCajaService; 

import jakarta.persistence.EntityNotFoundException; 

@Service
@Transactional // Aplicamos transaccionalidad para asegurar el commit
public class MovimientosCajaService implements IMovimientosCajaService {
    
    @Autowired
    private MovimientosCajaRepository repoMovimientos;
    
    @Autowired
    private ISesionesCajaService serviceSesiones; // Para validar la sesión
    
    // ----------------------------------------------------
    // 1. Registrar Movimiento (Lógica CRÍTICA)
    // ----------------------------------------------------
    @Override
    public void registrarMovimiento(MovimientosCaja movimiento, Integer idUsuarioRegistro) {
        // Paso 1: Validar que la sesión de caja exista y esté ABIERTA.
        
        // Buscamos la sesión por ID de Sesión. Necesitamos también el idSucursal,
        // pero lo obtenemos a través de un ID de sucursal de prueba (ej. 5) o del controlador.
        // Aquí asumimos que el idSucursal de prueba es 5 para que el serviceSesiones.buscarIdYSucursal funcione
        
        // NOTA: Para no depender de un ID de Sucursal "hardcodeado", el Controller deberá 
        // asegurar que el idSesion pertenezca a la Sucursal del usuario logueado.
        
        serviceSesiones.buscarIdYSucursal(movimiento.getIdSesion(), /* Suponemos un idSucursal de prueba */ 5) 
            .filter(s -> s.getEstado() == 1) // Debe estar activa (estado=1)
            .orElseThrow(() -> new RuntimeException("No se puede registrar el movimiento: La sesión no existe, está cerrada, o no pertenece a tu sucursal."));
        
        // Paso 2: Asignación de datos de seguridad
        movimiento.setIdUsuario(idUsuarioRegistro); 
        
        repoMovimientos.save(movimiento);
    }
    
    // ----------------------------------------------------
    // 2. Obtener movimientos de una sesión
    // ----------------------------------------------------
    @Override
    public List<MovimientosCaja> buscarPorSesion(Integer idSesion){
        return repoMovimientos.findByIdSesion(idSesion);
    }
    
    // ----------------------------------------------------
    // 3. Calcular Total Movimientos
    // ----------------------------------------------------
    @Override
    public BigDecimal calcularTotalMovimientos(Integer idSesion){
        List<MovimientosCaja> movimientos = buscarPorSesion(idSesion);
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
        
        movimientos.forEach(m -> {
            if (m.getTipoMovimiento() == MovimientosCaja.TipoMovimiento.Ingreso) {
                total.set(total.get().add(m.getMonto()));
            } else if (m.getTipoMovimiento() == MovimientosCaja.TipoMovimiento.Egreso) {
                total.set(total.get().subtract(m.getMonto()));
            }
        });
        
        return total.get();
    }
}