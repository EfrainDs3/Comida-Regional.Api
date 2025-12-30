package web.Regional_Api.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.PagoSuscripcionDTO;
import web.Regional_Api.service.PagoSuscripcionService;
@RestController
@RequestMapping("/api/pagos-suscripcion")

public class PagoSuscripcionController {
    
    @Autowired
    private PagoSuscripcionService pagoService;
    
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    /**
     * Obtener todos los pagos
     */
    @GetMapping
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerTodos() {
        List<PagoSuscripcionDTO> pagos = pagoService.obtenerTodos();
        return ResponseEntity.ok(pagos);
    }
    
    /**
     * Obtener pago por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagoSuscripcionDTO> obtenerPorId(@PathVariable Long id) {
        PagoSuscripcionDTO pago = pagoService.obtenerPorId(id);
        if (pago != null) {
            return ResponseEntity.ok(pago);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Obtener todos los pagos de un restaurante
     */
    @GetMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerPagosPorRestaurante(
            @PathVariable Integer idRestaurante) {
        List<PagoSuscripcionDTO> pagos = pagoService.obtenerPagosPorRestaurante(idRestaurante);
        return ResponseEntity.ok(pagos);
    }
    
    /**
     * Obtener último pago de un restaurante
     */
    @GetMapping("/restaurante/{idRestaurante}/ultimo")
    public ResponseEntity<PagoSuscripcionDTO> obtenerUltimoPago(
            @PathVariable Integer idRestaurante) {
        PagoSuscripcionDTO pago = pagoService.obtenerUltimoPago(idRestaurante);
        if (pago != null) {
            return ResponseEntity.ok(pago);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Obtener pagos pendientes
     */
    @GetMapping("/filtro/pendientes")
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerPagosPendientes() {
        List<PagoSuscripcionDTO> pagos = pagoService.obtenerPagosPendientes();
        return ResponseEntity.ok(pagos);
    }
    
    /**
     * Obtener pagos por estado
     */
    @GetMapping("/filtro/estado")
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerPagosPorEstado(
            @RequestParam String estado) {
        List<PagoSuscripcionDTO> pagos = pagoService.obtenerPagosPorEstado(estado);
        return ResponseEntity.ok(pagos);
    }
    
    /**
     * Obtener pagos de restaurante por estado
     */
    @GetMapping("/restaurante/{idRestaurante}/estado")
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerPagosRestaurantePorEstado(
            @PathVariable Integer idRestaurante,
            @RequestParam String estado) {
        List<PagoSuscripcionDTO> pagos = pagoService.obtenerPagosRestaurantePorEstado(idRestaurante, estado);
        return ResponseEntity.ok(pagos);
    }
    
    /**
     * Obtener pagos por rango de fechas
     */
    @GetMapping("/filtro/fecha")
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerPagosPorFecha(
            @RequestParam String desde,
            @RequestParam String hasta) {
        LocalDateTime desdeDate = LocalDateTime.parse(desde, dateFormatter);
        LocalDateTime hastaDate = LocalDateTime.parse(hasta, dateFormatter);
        List<PagoSuscripcionDTO> pagos = pagoService.obtenerPagosPorFecha(desdeDate, hastaDate);
        return ResponseEntity.ok(pagos);
    }
    
    /**
     * Contar pagos pendientes
     */
    @GetMapping("/estadisticas/pendientes-count")
    public ResponseEntity<Long> contarPagosPendientes() {
        long count = pagoService.contarPagosPendientes();
        return ResponseEntity.ok(count);
    }
    
    /**
     * Crear nuevo pago de suscripción
     */
    @PostMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<PagoSuscripcionDTO> crearPago(
            @PathVariable Integer idRestaurante,
            @RequestBody PagoSuscripcionDTO dto) {
        try {
            PagoSuscripcionDTO pago = pagoService.crearPago(idRestaurante, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(pago);
        } catch (RuntimeException e) {
            e.printStackTrace(); // Agregar logging para debug
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Aprobar pago de suscripción
     */
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<PagoSuscripcionDTO> aprobarPago(@PathVariable Long id) {
        try {
            PagoSuscripcionDTO pago = pagoService.aprobarPago(id);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Rechazar pago de suscripción
     */
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<PagoSuscripcionDTO> rechazarPago(
            @PathVariable Long id,
            @RequestParam String motivo) {
        try {
            PagoSuscripcionDTO pago = pagoService.rechazarPago(id, motivo);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Actualizar pago
     */
    @PutMapping("/{id}")
    public ResponseEntity<PagoSuscripcionDTO> actualizarPago(
            @PathVariable Long id,
            @RequestBody PagoSuscripcionDTO dto) {
        try {
            PagoSuscripcionDTO pago = pagoService.actualizarPago(id, dto);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Eliminar pago
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        try {
            pagoService.eliminarPago(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

