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

    @GetMapping("/caja/movimientos/{idSesion}")
    public List<MovimientosCaja> buscarPorSesion(@PathVariable("idSesion") Integer idSesion) {
     
        return serviceMovimientos.buscarPorSesion(idSesion);
    }
    
    
    @GetMapping("/caja/movimientos/todos")
    public List<MovimientosCaja> buscarTodos() {
        return serviceMovimientos.buscarTodos();
    }

    @PostMapping("/caja/movimientos")
    public MovimientosCaja registrarMovimiento(@RequestBody MovimientosCaja movimiento) { 
        serviceMovimientos.registrarMovimiento(movimiento);
        return movimiento; 
    }

    @PutMapping("/caja/movimientos")
    public MovimientosCaja modificarMovimiento(@RequestBody MovimientosCaja movimientoActualizado) {
        serviceMovimientos.modificarMovimiento(movimientoActualizado);
        return movimientoActualizado;
    }
     
    @DeleteMapping("/caja/movimientos/{id}")
    public String eliminarMovimiento(@PathVariable("id") Integer idMovimiento) {
        serviceMovimientos.eliminarMovimiento(idMovimiento);
        return "Movimiento eliminado exitosamente";
    }
}