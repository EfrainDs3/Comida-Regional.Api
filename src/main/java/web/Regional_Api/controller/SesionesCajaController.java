package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.SesionesCaja;
import web.Regional_Api.service.ISesionesCajaService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/restful")
public class SesionesCajaController {

    @Autowired
    private ISesionesCajaService serviceSesiones;

    
    @GetMapping("/caja/todos")
    public List<SesionesCaja> buscarTodasSinFiltro() {
        return serviceSesiones.buscarTodas(); 
    }
    
   
    @GetMapping("/caja")
    public List<SesionesCaja> buscarTodas() { 
        Integer idSucursalFijo = 1;
        return serviceSesiones.buscarTodasPorSucursal(idSucursalFijo);
    }
    
   
    public Optional<SesionesCaja> buscarAbierta() {
        Integer idSucursalFijo = 1;
        return serviceSesiones.buscarSesionAbiertaPorSucursal(idSucursalFijo);
    }


    @PostMapping("/caja")
    public SesionesCaja abrirCaja(@RequestBody SesionesCaja sesion) {
        serviceSesiones.abrirCaja(sesion);

        return sesion; 
    }

    
    // PUT: /restful/caja (CERRAR CAJA - ASUME ID de Sucursal/Usuario en el JSON)
    // -----------------------------------------------------------------------
    @PutMapping("/caja")
    public SesionesCaja cerrarCaja(@RequestBody SesionesCaja sesionCierre) {
        serviceSesiones.cerrarCaja(sesionCierre);  
        return sesionCierre; 
    }

    @DeleteMapping("/caja/{id}")
    public String eliminar(@PathVariable Integer id) {
        serviceSesiones.eliminar(id);
        
        return "Sesión eliminada"; 
    }
}