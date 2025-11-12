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

    // -----------------------------------------------------------------------
    // Bloques de Seguridad Multi-Tenant (COMENTADOS)
    // -----------------------------------------------------------------------
    /*
    private Integer getIdSucursalFromSecurityContext(String idSucursalHeader) {
        try {
            return Integer.parseInt(idSucursalHeader);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error de Seguridad: ID de Sucursal inválido o ausente.");
        }
    }

    private Integer getIdUsuarioFromSecurityContext(String idUsuarioHeader) {
        try {
            return Integer.parseInt(idUsuarioHeader);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error de Seguridad: ID de Usuario inválido o ausente.");
        }
    }
    */
    // -----------------------------------------------------------------------
    
    // 🌟 GET: /restful/caja/todos - NUEVO ENDPOINT PARA VER TODAS LAS SESIONES
    @GetMapping("/caja/todos")
    public List<SesionesCaja> buscarTodasSinFiltro() {
        return serviceSesiones.buscarTodas(); 
    }
    
    // -----------------------------------------------------------------------
    // GET: /restful/caja (ANTERIORMENTE BUSCABA POR SUCURSAL - AHORA ASUMIMOS ID FIJO 1)
    // -----------------------------------------------------------------------
    @GetMapping("/caja")
    public List<SesionesCaja> buscarTodas() { 
        // ⚠️ Modo de Prueba Rápida: Usamos ID 1 fijo.
        Integer idSucursalFijo = 1;
        return serviceSesiones.buscarTodasPorSucursal(idSucursalFijo);
    }
    
    // -----------------------------------------------------------------------
    // GET: /restful/caja/abierta (ANTERIORMENTE BUSCABA POR SUCURSAL - AHORA ASUMIMOS ID FIJO 1)
    // -----------------------------------------------------------------------
    @GetMapping("/caja/abierta")
    public Optional<SesionesCaja> buscarAbierta() {
        // ⚠️ Modo de Prueba Rápida: Usamos ID 1 fijo.
        Integer idSucursalFijo = 1;
        return serviceSesiones.buscarSesionAbiertaPorSucursal(idSucursalFijo);
    }

    // -----------------------------------------------------------------------
    // POST: /restful/caja (ABRIR CAJA - ASUME ID de Sucursal/Usuario en el JSON o valores fijos)
    // -----------------------------------------------------------------------
    @PostMapping("/caja")
    public SesionesCaja abrirCaja(@RequestBody SesionesCaja sesion) {
        
        // 🌟 Se asume que el objeto 'sesion' YA INCLUYE idSucursal e idUsuarioApertura.
        serviceSesiones.abrirCaja(sesion);
        
        return sesion; 
    }

    // -----------------------------------------------------------------------
    // PUT: /restful/caja (CERRAR CAJA - ASUME ID de Sucursal/Usuario en el JSON)
    // -----------------------------------------------------------------------
    @PutMapping("/caja")
    public SesionesCaja cerrarCaja(@RequestBody SesionesCaja sesionCierre) {
        
        // 🌟 Se asume que el objeto 'sesionCierre' YA INCLUYE idSucursal, idUsuarioCierre y idSesion.
        serviceSesiones.cerrarCaja(sesionCierre);
        
        return sesionCierre; 
    }

    // -----------------------------------------------------------------------
    // DELETE: /restful/caja/{id} (ELIMINAR)
    // -----------------------------------------------------------------------
    @DeleteMapping("/caja/{id}")
    public String eliminar(@PathVariable Integer id) {
        
        // 🌟 Simplificación: Ya no valida Multi-Tenant.
        serviceSesiones.eliminar(id);
        
        return "Sesión eliminada"; 
    }
}