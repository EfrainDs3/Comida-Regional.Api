package com.comidas.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    // --- Métodos Auxiliares de Seguridad (Simulación) ---

    // CRÍTICO: Usado para Multi-Tenant (viene del token/header del restaurante)
    private Integer getIdSucursalFromSecurityContext(String idSucursalHeader) {
        try {
            return Integer.parseInt(idSucursalHeader); 
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error de Seguridad: ID de Sucursal inválido o ausente.");
        }
    }
    
    // CRÍTICO: Usado para auditar aperturas y cierres (viene del token del usuario)
    private Integer getIdUsuarioFromSecurityContext(String idUsuarioHeader) {
        try {
            return Integer.parseInt(idUsuarioHeader); 
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error de Seguridad: ID de Usuario inválido o ausente.");
        }
    }

    // ----------------------------------------------------
    // GET: /restful/caja (Buscar todas las sesiones de la sucursal)
    // ----------------------------------------------------
    @GetMapping("/caja")
    public List<SesionesCaja> buscarTodas(
        @RequestHeader("X-Sucursal-ID") String idSucursalHeader) { 
        
        Integer idSucursal = getIdSucursalFromSecurityContext(idSucursalHeader);
        return serviceSesiones.buscarTodasPorSucursal(idSucursal);
    }
    
    // ----------------------------------------------------
    // GET: /restful/caja/abierta (Buscar la sesión abierta actual)
    // ----------------------------------------------------
    @GetMapping("/caja/abierta")
    public Optional<SesionesCaja> buscarAbierta(
            @RequestHeader("X-Sucursal-ID") String idSucursalHeader) { 
            
        Integer idSucursal = getIdSucursalFromSecurityContext(idSucursalHeader);
        return serviceSesiones.buscarSesionAbiertaPorSucursal(idSucursal);
    }

    // ----------------------------------------------------
    // POST: /restful/caja (ABRIR CAJA - Requiere Monto Inicial)
    // ----------------------------------------------------
    @PostMapping("/caja")
    public SesionesCaja abrirCaja(@RequestBody SesionesCaja sesion,
                                  @RequestHeader("X-Sucursal-ID") String idSucursalHeader,
                                  @RequestHeader("X-Usuario-ID") String idUsuarioHeader) { // Simulación de Usuario logueado
        
        Integer idSucursal = getIdSucursalFromSecurityContext(idSucursalHeader);
        Integer idUsuario = getIdUsuarioFromSecurityContext(idUsuarioHeader);

        serviceSesiones.abrirCaja(sesion, idSucursal, idUsuario);
        
        return sesion; 
    }

    // ----------------------------------------------------
    // PUT: /restful/caja (CERRAR CAJA - Requiere Monto Final Real)
    // ----------------------------------------------------
    // El cuerpo debe contener idSesion y montoFinalReal
    @PutMapping("/caja")
    public SesionesCaja cerrarCaja(@RequestBody SesionesCaja sesionCierre,
                                   @RequestHeader("X-Sucursal-ID") String idSucursalHeader,
                                   @RequestHeader("X-Usuario-ID") String idUsuarioHeader) {
        
        Integer idSucursal = getIdSucursalFromSecurityContext(idSucursalHeader);
        Integer idUsuario = getIdUsuarioFromSecurityContext(idUsuarioHeader);
        
        serviceSesiones.cerrarCaja(sesionCierre, idSucursal, idUsuario);
        
        return sesionCierre; 
    }

    // ----------------------------------------------------
    // DELETE: /restful/caja/{id} (ELIMINAR LÓGICO)
    // ----------------------------------------------------
    @DeleteMapping("/caja/{id}")
    public String eliminar(@PathVariable Integer id,
                           @RequestHeader("X-Sucursal-ID") String idSucursalHeader) {
        
        Integer idSucursal = getIdSucursalFromSecurityContext(idSucursalHeader);
        
        // 1. Validar que la sesión exista y pertenezca
        serviceSesiones.buscarIdYSucursal(id, idSucursal)
            .orElseThrow(() -> new EntityNotFoundException("Sesión no encontrada o acceso denegado."));
            
        // 2. Ejecutar Soft Delete
        serviceSesiones.eliminar(id, idSucursal);
        
        return "Sesión eliminada"; 
    }
}