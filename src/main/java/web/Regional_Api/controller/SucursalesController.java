package web.Regional_Api.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader; // CRÍTICO para seguridad Multi-Tenant

import com.comidas.api.entity.Sucursales;
import com.comidas.api.service.ISucursalesService;

import jakarta.persistence.EntityNotFoundException; 

@RestController
@RequestMapping("/restful") 
public class SucursalesController {

    @Autowired
    private ISucursalesService serviceSucursales;

    // Método auxiliar para simular la extracción del idRestaurante (del JWT/Security Context)
    private Integer getIdRestauranteFromSecurityContext(String authorizationHeader) {
        try {
            return Integer.parseInt(authorizationHeader); 
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error de Autenticación: ID de Restaurante inválido o ausente.");
        }
    }


    // --------------------------------------------------------------------------------------
    // GET: /restful/sucursales (BUSCAR TODOS - Multi-Tenant)
    // --------------------------------------------------------------------------------------
    @GetMapping("/sucursales")
    public List<Sucursales> buscarTodos(
        @RequestHeader("X-Restaurante-ID") String idRestauranteHeader) { 
        
        Integer idRestaurante = getIdRestauranteFromSecurityContext(idRestauranteHeader);
        return serviceSucursales.buscarTodosPorRestaurante(idRestaurante);
    }
    
    // --------------------------------------------------------------------------------------
    // GET: /restful/sucursales/{id} (BUSCAR POR ID - Multi-Tenant)
    // --------------------------------------------------------------------------------------
    @GetMapping("/sucursales/{id}")
    public Optional<Sucursales> buscarId(
            @PathVariable("id") Integer idSucursal,
            @RequestHeader("X-Restaurante-ID") String idRestauranteHeader) { 
        
        Integer idRestaurante = getIdRestauranteFromSecurityContext(idRestauranteHeader);
        
        // El servicio realiza la búsqueda y valida la pertenencia
        return serviceSucursales.buscarIdYRestaurante(idSucursal, idRestaurante);
    }
    
    // --------------------------------------------------------------------------------------
    // POST: /restful/sucursales (GUARDAR - Asigna el restaurante automáticamente)
    // --------------------------------------------------------------------------------------
    @PostMapping("/sucursales")
    public Sucursales guardar(@RequestBody Sucursales sucursal,
                              @RequestHeader("X-Restaurante-ID") String idRestauranteHeader) {
        
        Integer idRestaurante = getIdRestauranteFromSecurityContext(idRestauranteHeader);
        serviceSucursales.guardar(sucursal, idRestaurante); // Asigna el idRestaurante desde el token
        
        return sucursal; 
    }

    // --------------------------------------------------------------------------------------
    // PUT: /restful/sucursales (MODIFICAR - Con validación Multi-Tenant)
    // --------------------------------------------------------------------------------------
    @PutMapping("/sucursales")
    public Sucursales modificar(@RequestBody Sucursales sucursalActualizada,
                                @RequestHeader("X-Restaurante-ID") String idRestauranteHeader) {
        
        Integer idRestaurante = getIdRestauranteFromSecurityContext(idRestauranteHeader);
        
        // 1. Validamos la pertenencia (Control de acceso Multi-Tenant)
        serviceSucursales.buscarIdYRestaurante(sucursalActualizada.getIdSucursal(), idRestaurante)
            .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada o acceso denegado para modificar."));
        
        // 2. Ejecutamos la modificación
        serviceSucursales.modificar(sucursalActualizada, idRestaurante);
        
        return sucursalActualizada;
    }

    // --------------------------------------------------------------------------------------
    // DELETE: /restful/sucursales/{id} (ELIMINAR LÓGICO - Con validación Multi-Tenant)
    // --------------------------------------------------------------------------------------
    @DeleteMapping("/sucursales/{id}")
    public String eliminar(@PathVariable Integer id,
                           @RequestHeader("X-Restaurante-ID") String idRestauranteHeader) {
        
        Integer idRestaurante = getIdRestauranteFromSecurityContext(idRestauranteHeader);
        
        // 1. Validamos la pertenencia del recurso
        serviceSucursales.buscarIdYRestaurante(id, idRestaurante)
            .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada o acceso denegado para eliminar."));
            
    // 2. Eliminamos (Soft Delete)
    serviceSucursales.eliminar(id, idRestaurante);
        
        return "Sucursal eliminada"; 
    }
}