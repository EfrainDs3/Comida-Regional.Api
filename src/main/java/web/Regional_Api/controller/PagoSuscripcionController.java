package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.PagoSuscripcion;
import web.Regional_Api.entity.PagoSuscripcionDTO;
import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.service.IPagoSuscripcionService;
import web.Regional_Api.service.IRestauranteService;

@RestController
@RequestMapping("/restful/pagos")
@CrossOrigin(origins = "*")
public class PagoSuscripcionController {

    @Autowired
    private IPagoSuscripcionService pagoService;
    
    
    @Autowired
    private IRestauranteService restauranteService;

    // 1. GET (Todos)
    @GetMapping
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerTodos() {
        List<PagoSuscripcionDTO> pagos = pagoService.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagos);
    }

    // 2. GET (Por ID)
    @GetMapping("/{id}")
    public ResponseEntity<PagoSuscripcionDTO> obtenerPorId(@PathVariable Integer id) {
        return pagoService.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 3. GET (Historial por Restaurante - RF10)
    @GetMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerPagosPorRestaurante(@PathVariable Integer idRestaurante) {
        // (Opcional) Verificar si el restaurante existe
        if (restauranteService.buscarId(idRestaurante).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PagoSuscripcionDTO> pagos = pagoService.buscarPorIdRestaurante(idRestaurante).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagos);
    }
    // 4. POST (Crear)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PagoSuscripcionDTO dto) {
        
        Optional<Restaurante> optRestaurante = restauranteService.buscarId(dto.getId_restaurante());
        if (optRestaurante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: El restaurante con ID " + dto.getId_restaurante() + " no existe.");
        }
        
        PagoSuscripcion pago = new PagoSuscripcion();
        pago.setRestaurante(optRestaurante.get());
        
        // Mapeo directo (Nombres coinciden)
        pago.setFecha_pago(dto.getFecha_pago());
        pago.setMonto_pagado(dto.getMonto_pagado());
        pago.setFecha_inicio_suscripcion(dto.getFecha_inicio_suscripcion());
        pago.setFecha_fin_suscripcion(dto.getFecha_fin_suscripcion());
        
        // Estado por defecto (1) si no viene en el DTO
        if(dto.getEstado_pago() != null) {
            pago.setEstado_pago(dto.getEstado_pago());
        }

        try {
            PagoSuscripcion nuevoPago = pagoService.guardar(pago);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al guardar el pago: " + e.getMessage());
        }
    }

    // 5. PUT (Actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody PagoSuscripcionDTO dto) {
        
        Optional<PagoSuscripcion> optPago = pagoService.buscarId(id);
        if (optPago.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
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
        pago.setFecha_inicio_suscripcion(dto.getFecha_inicio_suscripcion());
        pago.setFecha_fin_suscripcion(dto.getFecha_fin_suscripcion());
        pago.setEstado_pago(dto.getEstado_pago());

    PagoSuscripcion actualizado = pagoService.guardar(pago);
    return ResponseEntity.ok(convertirADTO(actualizado));
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

    private PagoSuscripcionDTO convertirADTO(PagoSuscripcion entidad) {
        PagoSuscripcionDTO dto = new PagoSuscripcionDTO();
        dto.setId_pago(entidad.getId_pago());
        dto.setId_restaurante(entidad.getRestaurante() != null ? entidad.getRestaurante().getId_restaurante() : null);
        dto.setFecha_pago(entidad.getFecha_pago());
        dto.setMonto_pagado(entidad.getMonto_pagado());
        dto.setFecha_inicio_suscripcion(entidad.getFecha_inicio_suscripcion());
        dto.setFecha_fin_suscripcion(entidad.getFecha_fin_suscripcion());
        dto.setEstado_pago(entidad.getEstado_pago());
        return dto;
    }
}