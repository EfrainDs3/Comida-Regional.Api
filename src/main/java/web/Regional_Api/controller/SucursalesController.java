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
import web.Regional_Api.entity.Sucursales;
import web.Regional_Api.service.ISucursalesService;
// web.Regional_Api.service.jpa.UsuarioService ya no es necesario aquí en modo prueba
import web.Regional_Api.service.jpa.UsuarioService; 

import jakarta.persistence.EntityNotFoundException; 

@RestController
@RequestMapping("/restful") 
public class SucursalesController {

    @Autowired
    private ISucursalesService serviceSucursales;
    
    // El UsuarioService se mantiene, pero no es usado en modo de prueba.
    @Autowired
    private UsuarioService usuarioService; 

    // Bloque Multi-Tenant (COMENTADO PARA PRUEBAS)
    /*
    private Integer getIdRestauranteFromSecurityContext() {
        // ... Lógica Multi-Tenant original
    }
    */
    
    // --------------------------------------------------------------------------------------
    // GET: /restful/sucursales/todos (BUSCAR TODOS - SIN FILTRO)
    // --------------------------------------------------------------------------------------
    @GetMapping("/sucursales/todos") 
    public List<Sucursales> buscarTodasSinFiltro() { 
        return serviceSucursales.buscarTodos(); 
    }

    // --------------------------------------------------------------------------------------
    // GET: /restful/sucursales (Filtrado - OBSOLETO, mantenido para evitar error de compilación)
    // --------------------------------------------------------------------------------------
    @GetMapping("/sucursales")
    public List<Sucursales> buscarTodosFiltradoPorRestaurante() { 
        // ⚠️ USANDO ID FIJO PARA COMPILAR, PERO DEBES USAR /todos
        Integer idRestauranteFijo = 1; 
        return serviceSucursales.buscarTodosPorRestaurante(idRestauranteFijo);
    }
    
    // --------------------------------------------------------------------------------------
    // GET: /restful/sucursales/{id} (BUSCAR POR ID - Simplificado)
    // --------------------------------------------------------------------------------------
    @GetMapping("/sucursales/{id}")
    public Optional<Sucursales> buscarId(
            @PathVariable("id") Integer idSucursal) { 
        
        // 🌟 CORRECCIÓN: Llamamos al método simple de búsqueda por ID
        return serviceSucursales.buscarId(idSucursal); 
    }
    
    // --------------------------------------------------------------------------------------
    // POST: /restful/sucursales (GUARDAR - SIN ASIGNACIÓN DE ID FIJO)
    // --------------------------------------------------------------------------------------
    @PostMapping("/sucursales")
    public Sucursales guardar(@RequestBody Sucursales sucursal) {
        
        // 🌟 CORRECCIÓN: Llamamos al método sin el idRestaurante
        serviceSucursales.guardar(sucursal); 
        
        return sucursal; 
    }

    // --------------------------------------------------------------------------------------
    // PUT: /restful/sucursales (MODIFICAR - SIN VALIDACIÓN MULTI-TENANT)
    // --------------------------------------------------------------------------------------
    @PutMapping("/sucursales")
    public Sucursales modificar(@RequestBody Sucursales sucursalActualizada) {
        
        // 🌟 CORRECCIÓN: Llamamos al método sin validación de ID
        serviceSucursales.modificar(sucursalActualizada);
        
        return sucursalActualizada;
    }

    // --------------------------------------------------------------------------------------
    // DELETE: /restful/sucursales/{id} (ELIMINAR - SIN VALIDACIÓN MULTI-TENANT)
    // --------------------------------------------------------------------------------------
    @DeleteMapping("/sucursales/{id}")
    public String eliminar(@PathVariable Integer id) {
        
        // 🌟 CORRECCIÓN: Llamamos al método sin validación de ID
        serviceSucursales.eliminar(id);
        
        return "Sucursal eliminada"; 
    }
}