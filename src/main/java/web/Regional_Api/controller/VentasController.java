package web.Regional_Api.controller;

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
import web.Regional_Api.entity.Ventas;
import web.Regional_Api.service.IVentasService;

@RestController
@RequestMapping("/restful")
public class VentasController {

    @Autowired
    private IVentasService serviceVentas;

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
            return null;
        }
    }
    @GetMapping("/ventas/sesion/{idSesion}")
    public List<Ventas> buscarTodasPorSesion(@PathVariable("idSesion") Integer idSesion) {
        // En un sistema real, se debería validar que la sesión pertenezca a la sucursal del usuario
        return serviceVentas.buscarTodasPorSesion(idSesion);
    }
    
    @GetMapping("/ventas/{id}")
    public Optional<Ventas> buscarId(@PathVariable("id") Integer idVenta) {
        return serviceVentas.buscarId(idVenta);
    }
    
    @PostMapping("/ventas")
    public Ventas registrarVenta(@RequestBody Ventas venta,
                                 @RequestHeader("X-Sesion-ID") String idSesionHeader,
                                 @RequestHeader(value = "X-Cliente-ID", required = false) String idClienteHeader) {
        Integer idSesion = getIdSesionFromHeader(idSesionHeader);
        Integer idCliente = getIdClienteFromHeader(idClienteHeader);

        return serviceVentas.registrarVenta(venta, idSesion, idCliente);
    }
    @DeleteMapping("/ventas/{id}")
    public String anularVenta(@PathVariable Integer id) {
        serviceVentas.anularVenta(id);
        return "Venta anulada (eliminación lógica)"; 
    }
}