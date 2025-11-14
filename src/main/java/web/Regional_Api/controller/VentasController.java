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
import web.Regional_Api.entity.Ventas;
import web.Regional_Api.service.IVentasService;

@RestController
@RequestMapping("/restful")
public class VentasController {

    @Autowired
    private IVentasService serviceVentas;

  
    @GetMapping("/ventas/todos")
    public List<Ventas> buscarTodas() {
        return serviceVentas.buscarTodas(); 
    }
    
    @GetMapping("/ventas/sesion/{idSesion}")
    public List<Ventas> buscarTodasPorSesion(@PathVariable("idSesion") Integer idSesion) {
        return serviceVentas.buscarTodasPorSesion(idSesion);
    }

    @GetMapping("/ventas/{id}")
    public Optional<Ventas> buscarId(@PathVariable("id") Integer idVenta) {
        return serviceVentas.buscarId(idVenta);
    }
    

    @PostMapping("/ventas")
    public Ventas registrarVenta(@RequestBody Ventas venta) {
        return serviceVentas.registrarVenta(venta);
    }
    
    @PutMapping("/ventas")
    public Ventas modificarVenta(@RequestBody Ventas ventaActualizada) {
        return serviceVentas.modificarVenta(ventaActualizada);
    }

    @DeleteMapping("/ventas/{id}")
    public String anularVenta(@PathVariable Integer id) {
        serviceVentas.anularVenta(id);
        return "Venta anulada (eliminación lógica)"; 
    }
}