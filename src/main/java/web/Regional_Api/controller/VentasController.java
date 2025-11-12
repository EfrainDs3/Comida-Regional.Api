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

    // -----------------------------------------------------------------------
    // Bloques de Seguridad (COMENTADOS/ELIMINADOS)
    // -----------------------------------------------------------------------
    /*
    private Integer getIdSesionFromHeader(String idSesionHeader) { ... }
    private Integer getIdClienteFromHeader(String idClienteHeader) { ... }
    */
    // -----------------------------------------------------------------------

    // GET: /restful/ventas/todos (NUEVO: Para listar todas las ventas sin filtro)
    @GetMapping("/ventas/todos")
    public List<Ventas> buscarTodas() {
        return serviceVentas.buscarTodas(); 
    }
    
    // GET: /restful/ventas/sesion/{idSesion} (BUSCAR POR SESIÓN)
    @GetMapping("/ventas/sesion/{idSesion}")
    public List<Ventas> buscarTodasPorSesion(@PathVariable("idSesion") Integer idSesion) {
        // Asume que la validación de pertenencia se hace con el ID de la sesión.
        return serviceVentas.buscarTodasPorSesion(idSesion);
    }
    
    // GET: /restful/ventas/{id} (BUSCAR POR ID)
    @GetMapping("/ventas/{id}")
    public Optional<Ventas> buscarId(@PathVariable("id") Integer idVenta) {
        return serviceVentas.buscarId(idVenta);
    }
    
    // POST: /restful/ventas (REGISTRAR VENTA - SIMPLIFICADO)
    @PostMapping("/ventas")
    public Ventas registrarVenta(@RequestBody Ventas venta) {
        // 🌟 SIMPLIFICACIÓN: El objeto 'venta' DEBE incluir idSesion y, opcionalmente, idCliente en el JSON.
        return serviceVentas.registrarVenta(venta);
    }
    
    // 🌟 NUEVO: PUT: /restful/ventas (MODIFICAR VENTA)
    // -----------------------------------------------------------------------
    @PutMapping("/ventas")
    public Ventas modificarVenta(@RequestBody Ventas ventaActualizada) {
        // Asumimos que el servicio IVentasService ya tiene el método modificarVenta
        // que toma el objeto 'Ventas' con su ID y lo actualiza.
        return serviceVentas.modificarVenta(ventaActualizada);
    }

    // DELETE: /restful/ventas/{id} (ANULAR VENTA - SIN FILTRO)
    @DeleteMapping("/ventas/{id}")
    public String anularVenta(@PathVariable Integer id) {
        serviceVentas.anularVenta(id);
        return "Venta anulada (eliminación lógica)"; 
    }




}