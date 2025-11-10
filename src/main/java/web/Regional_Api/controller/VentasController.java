package com.comidas.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;  
import web.Regional_Api.entity.Ventas;
import web.Regional_Api.service.IVentasService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/restful")
public class VentasController {

    @Autowired
    private IVentasService serviceVentas;

    // Métodos Auxiliares de Seguridad (Simulación)
    private Integer getIdSesionFromHeader(String idSesionHeader) {
        try {
            return Integer.parseInt(idSesionHeader); 
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error: ID de Sesión inválido o ausente.");
        }
    }
    
    private Integer getIdClienteFromHeader(String idClienteHeader) {
        try {
            return Integer.parseInt(idClienteHeader); 
        } catch (NumberFormatException e) {
            // Permitimos null si el cliente no es requerido (venta anónima)
            return null; 
        }
    }


    // ----------------------------------------------------
    // GET: /restful/ventas/sesion/{idSesion} (Buscar todas por Sesión)
    // ----------------------------------------------------
    @GetMapping("/ventas/sesion/{idSesion}")
    public List<Ventas> buscarTodasPorSesion(@PathVariable("idSesion") Integer idSesion) {
        // En un sistema real, se debería validar que la sesión pertenezca a la sucursal del usuario
        return serviceVentas.buscarTodasPorSesion(idSesion);
    }
    
    // ----------------------------------------------------
    // GET: /restful/ventas/{id} (Buscar por ID)
    // ----------------------------------------------------
    @GetMapping("/ventas/{id}")
    public Optional<Ventas> buscarId(@PathVariable("id") Integer idVenta) {
        return serviceVentas.buscarId(idVenta);
    }
    
    // ----------------------------------------------------
    // POST: /restful/ventas (Registrar Venta desde un Pedido)
    // ----------------------------------------------------
    // Se requiere: idPedido (en el body), idSesion y idCliente (en headers)
    @PostMapping("/ventas")
    public Ventas registrarVenta(@RequestBody Ventas venta,
                                 @RequestHeader("X-Sesion-ID") String idSesionHeader,
                                 @RequestHeader(value = "X-Cliente-ID", required = false) String idClienteHeader) {
        
        Integer idSesion = getIdSesionFromHeader(idSesionHeader);
        Integer idCliente = getIdClienteFromHeader(idClienteHeader);
        
        // Aquí, venta.idPedido debe venir en el cuerpo (body)
        Ventas nuevaVenta = serviceVentas.registrarVenta(venta, idSesion, idCliente);
        
        return nuevaVenta;
    }

    // ----------------------------------------------------
    // DELETE: /restful/ventas/{id} (Anular/Soft Delete)
    // ----------------------------------------------------
    @DeleteMapping("/ventas/{id}")
    public String anularVenta(@PathVariable Integer id) {
        serviceVentas.anularVenta(id);
        return "Venta anulada (eliminación lógica)"; 
    }
}