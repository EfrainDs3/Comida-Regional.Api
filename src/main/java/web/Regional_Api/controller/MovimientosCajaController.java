package com.comidas.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comidas.api.entity.MovimientosCaja;
import com.comidas.api.service.IMovimientosCajaService;
import com.comidas.api.service.ISesionesCajaService; // Para validaciones futuras

@RestController
@RequestMapping("/restful")
public class MovimientosCajaController {

    @Autowired
    private IMovimientosCajaService serviceMovimientos;
    
    // Asumimos que también inyectarás ISesionesCajaService para obtener la ID de la Sucursal/Restaurante
    @Autowired
    private ISesionesCajaService serviceSesiones; 


    // Método Auxiliar de Seguridad (Simulación)
    private Integer getIdUsuarioFromSecurityContext(String idUsuarioHeader) {
        try {
            return Integer.parseInt(idUsuarioHeader); 
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error de Seguridad: ID de Usuario inválido o ausente.");
        }
    }

    // ----------------------------------------------------
    // GET: /restful/caja/movimientos/{idSesion} (Buscar movimientos por Sesión)
    // ----------------------------------------------------
    @GetMapping("/caja/movimientos/{idSesion}")
    public List<MovimientosCaja> buscarPorSesion(@PathVariable("idSesion") Integer idSesion) {
        // Nota: La validación de que la Sesión pertenece a la Sucursal del usuario 
        // debe hacerse aquí si la seguridad fuera completa. Por ahora, solo listamos.
        return serviceMovimientos.buscarPorSesion(idSesion);
    }
    
    // ----------------------------------------------------
    // POST: /restful/caja/movimientos (Registrar Ingreso/Egreso)
    // ----------------------------------------------------
    @PostMapping("/caja/movimientos")
    public MovimientosCaja registrarMovimiento(@RequestBody MovimientosCaja movimiento,
                                               @RequestHeader("X-Usuario-ID") String idUsuarioHeader) {
        
        Integer idUsuario = getIdUsuarioFromSecurityContext(idUsuarioHeader);
        
        // La validación de que la sesión esté abierta la maneja el Service
        serviceMovimientos.registrarMovimiento(movimiento, idUsuario);
        
        return movimiento; 
    }
}