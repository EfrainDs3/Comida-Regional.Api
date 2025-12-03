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
import web.Regional_Api.service.jpa.UsuarioService; 

import jakarta.persistence.EntityNotFoundException; 

@RestController
@RequestMapping("/restful") 
public class SucursalesController {

    @Autowired
    private ISucursalesService serviceSucursales;
    
 
    @Autowired
    private UsuarioService usuarioService; 

    @GetMapping("/sucursales/todos") 
    public List<Sucursales> buscarTodasSinFiltro() { 
        return serviceSucursales.buscarTodos(); 
    }


    @GetMapping("/sucursales")
    public List<Sucursales> buscarTodosFiltradoPorRestaurante() { 
        Integer idRestauranteFijo = 1; 
        return serviceSucursales.buscarTodosPorRestaurante(idRestauranteFijo);
    }
    
   
    @GetMapping("/sucursales/{id}")
    public Optional<Sucursales> buscarId(
            @PathVariable("id") Integer idSucursal) { 
        return serviceSucursales.buscarId(idSucursal); 
    }
    
  @PostMapping("/sucursales")
    public Sucursales guardar(@RequestBody Sucursales sucursal) {
        // CORRECCIÓN: Llamamos al servicio y retornamos lo que el servicio nos da
        return serviceSucursales.guardar(sucursal); 
    }

    
    @PutMapping("/sucursales")
    public Sucursales modificar(@RequestBody Sucursales sucursalActualizada) {
        serviceSucursales.modificar(sucursalActualizada);
        return sucursalActualizada;
    }

    @DeleteMapping("/sucursales/{id}")
    public String eliminar(@PathVariable Integer id) {
        serviceSucursales.eliminar(id);
        return "Sucursal eliminada"; 
    }
}