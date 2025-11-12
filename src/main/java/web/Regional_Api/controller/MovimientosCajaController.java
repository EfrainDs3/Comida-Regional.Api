package web.Regional_Api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping; // Añadir import
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; // Añadir import
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.MovimientosCaja;
import web.Regional_Api.service.IMovimientosCajaService;
import web.Regional_Api.service.ISesionesCajaService;
import jakarta.persistence.EntityNotFoundException; // Añadir import si no está

@RestController
@RequestMapping("/restful")
public class MovimientosCajaController {

    @Autowired
    private IMovimientosCajaService serviceMovimientos;
    
    @Autowired
    private ISesionesCajaService serviceSesiones; 

    // --------------------------------------------------------------------------------------
    // Bloque de Seguridad Multi-Tenant (COMENTADO PARA PRUEBAS)
    // --------------------------------------------------------------------------------------
    /*
    private Integer getIdUsuarioFromSecurityContext(String idUsuarioHeader) {
        try {
            return Integer.parseInt(idUsuarioHeader); 
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error de Seguridad: ID de Usuario inválido o ausente.");
        }
    }
    */
    // --------------------------------------------------------------------------------------

    // GET: /restful/caja/movimientos/{idSesion} (BUSCAR POR SESIÓN)
    @GetMapping("/caja/movimientos/{idSesion}")
    public List<MovimientosCaja> buscarPorSesion(@PathVariable("idSesion") Integer idSesion) {
        // No requiere filtro de usuario/sucursal en esta etapa
        return serviceMovimientos.buscarPorSesion(idSesion);
    }
    
    // GET: /restful/caja/movimientos/todos (NUEVO: Buscar todos los movimientos)
    @GetMapping("/caja/movimientos/todos")
    public List<MovimientosCaja> buscarTodos() {
        return serviceMovimientos.buscarTodos();
    }


    // POST: /restful/caja/movimientos (REGISTRAR MOVIMIENTO - SIMPLIFICADO)
    @PostMapping("/caja/movimientos")
    public MovimientosCaja registrarMovimiento(@RequestBody MovimientosCaja movimiento) {
        
        // 🌟 SIMPLIFICACIÓN: El objeto MovimientosCaja debe incluir idUsuario.
        // La lógica del servicio se encargará de la validación.
        serviceMovimientos.registrarMovimiento(movimiento);
        
        return movimiento; 
    }

    // 🌟 NUEVO: PUT: /restful/caja/movimientos (MODIFICAR MOVIMIENTO - SIN FILTRO)
    @PutMapping("/caja/movimientos")
    public MovimientosCaja modificarMovimiento(@RequestBody MovimientosCaja movimientoActualizado) {
        
        // 🌟 SIMPLIFICACIÓN: Solo llama al servicio para actualizar.
        serviceMovimientos.modificarMovimiento(movimientoActualizado);
        
        return movimientoActualizado;
    }
    
    // 🌟 NUEVO: DELETE: /restful/caja/movimientos/{id} (ELIMINAR MOVIMIENTO - SIN FILTRO)
    @DeleteMapping("/caja/movimientos/{id}")
    public String eliminarMovimiento(@PathVariable("id") Integer idMovimiento) {
        
        // 🌟 SIMPLIFICACIÓN: Solo llama al servicio para eliminar por ID.
        serviceMovimientos.eliminarMovimiento(idMovimiento);
        
        return "Movimiento eliminado exitosamente";
    }
}