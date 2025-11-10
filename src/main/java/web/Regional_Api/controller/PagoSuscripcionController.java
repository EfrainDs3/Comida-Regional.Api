package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.Regional_Api.entity.PagoSuscripcionDTO;
import web.Regional_Api.entity.PagoSuscripcion;
import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.service.IPagoSuscripcionService;
import web.Regional_Api.service.IRestauranteService; // Necesario para la FK

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoSuscripcionController {

    @Autowired
    private IPagoSuscripcionService pagoService;
    
    // Inyectamos el servicio de Restaurante para validar la FK
    @Autowired
    private IRestauranteService restauranteService;

    // 1. GET (Todos)
    @GetMapping
    public ResponseEntity<List<PagoSuscripcion>> obtenerTodos() {
        List<PagoSuscripcion> pagos = pagoService.buscarTodos();
        return ResponseEntity.ok(pagos);
    }

    // 2. GET (Por ID)
    @GetMapping("/{id}")
    public ResponseEntity<PagoSuscripcion> obtenerPorId(@PathVariable Integer id) {
        return pagoService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 3. GET (Historial por Restaurante - RF10)
    @GetMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<List<PagoSuscripcion>> obtenerPagosPorRestaurante(@PathVariable Integer idRestaurante) {
        // (Opcional) Verificar si el restaurante existe
        if (restauranteService.buscarId(idRestaurante).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PagoSuscripcion> pagos = pagoService.buscarPorIdRestaurante(idRestaurante);
        return ResponseEntity.ok(pagos);
    }

    // 4. POST (Crear)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PagoSuscripcionDTO dto) {
        
        // Verificación de FK: El restaurante debe existir
        Optional<Restaurante> optRestaurante = restauranteService.buscarId(dto.getId_restaurante());
        if (optRestaurante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede crear el pago: El restaurante con ID " + dto.getId_restaurante() + " no existe.");
        }
        
        // Mapeo simple DTO -> Entidad
        PagoSuscripcion pago = new PagoSuscripcion();
        pago.setRestaurante(optRestaurante.get()); // Asignamos el objeto
        
        pago.setFecha_pago(dto.getFecha_pago());
        pago.setMonto_pagado(dto.getMonto_pagado());
        pago.setTipo_suscripcion(dto.getTipo_suscripcion());
        pago.setFecha_inicio_suscripcion(dto.getFecha_inicio_suscripcion());
        pago.setFecha_fin_suscripcion(dto.getFecha_fin_suscripcion());
        pago.setMetodo_pago(dto.getMetodo_pago());
        

        PagoSuscripcion nuevoPago = pagoService.guardar(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    // 5. PUT (Actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody PagoSuscripcionDTO dto) {
        
        Optional<PagoSuscripcion> optPago = pagoService.buscarId(id);
        if (optPago.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Verificación de FK: El restaurante debe existir
        Optional<Restaurante> optRestaurante = restauranteService.buscarId(dto.getId_restaurante());
        if (optRestaurante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede actualizar: El restaurante con ID " + dto.getId_restaurante() + " no existe.");
        }
        
        // Mapeo simple DTO -> Entidad
        PagoSuscripcion pago = optPago.get();
        pago.setRestaurante(optRestaurante.get()); // Asignamos el objeto
        
        pago.setFecha_pago(dto.getFecha_pago());
        pago.setMonto_pagado(dto.getMonto_pagado());
        pago.setTipo_suscripcion(dto.getTipo_suscripcion());
        pago.setFecha_inicio_suscripcion(dto.getFecha_inicio_suscripcion());
        pago.setFecha_fin_suscripcion(dto.getFecha_fin_suscripcion());
        pago.setMetodo_pago(dto.getMetodo_pago());
        pago.setEstado_pago(dto.getEstado_pago());

        PagoSuscripcion actualizado = pagoService.guardar(pago);
        return ResponseEntity.ok(actualizado);
    }

    // 6. DELETE (Borrado Físico porque no hay 'estado ctmre')
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (pagoService.buscarId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}