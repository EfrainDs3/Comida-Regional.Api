package web.Regional_Api.controller;

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

import web.Regional_Api.entity.MovimientosCaja;
import web.Regional_Api.service.IMovimientosCajaService;
import web.Regional_Api.service.ISesionesCajaService;





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

 
    @GetMapping("/caja/movimientos/{idSesion}")
    public List<MovimientosCaja> buscarPorSesion(@PathVariable("idSesion") Integer idSesion) {

        return serviceMovimientos.buscarPorSesion(idSesion);
    }


    @PostMapping("/caja/movimientos")
    public MovimientosCaja registrarMovimiento(@RequestBody MovimientosCaja movimiento,
                                               @RequestHeader("X-Usuario-ID") String idUsuarioHeader) {
        
        Integer idUsuario = getIdUsuarioFromSecurityContext(idUsuarioHeader);
        
        // La validación de que la sesión esté abierta la maneja el Service
        serviceMovimientos.registrarMovimiento(movimiento, idUsuario);
        
        return movimiento; 
    }
}