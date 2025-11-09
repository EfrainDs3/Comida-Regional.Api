package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.PagoSuscripcion;
import web.Regional_Api.entity.PagoSuscripcionDTO;
import web.Regional_Api.entity.Restaurante; // Necesitamos la entidad Restaurante
import web.Regional_Api.repository.PagoSuscripcionRepository;
import web.Regional_Api.repository.RestauranteRepository; // Necesitamos el repo de Restaurante

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoSuscripcionController {

    @Autowired
    private PagoSuscripcionRepository pagoRepository;
    
    // Necesitamos el repositorio de Restaurante para verificar que el ID existe
    // y para asignarlo al crear el pago.
    @Autowired
    private RestauranteRepository restauranteRepository;

    // RF09: Registrar un nuevo pago
    @PostMapping
    public ResponseEntity<?> crearPago(@RequestBody PagoSuscripcionDTO pagoDTO) {
        
        // 1. Validar que el restaurante existe
        Optional<Restaurante> optRestaurante = restauranteRepository.findById(pagoDTO.getId_restaurante());
        if (optRestaurante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("Error: El restaurante con ID " + pagoDTO.getId_restaurante() + " no existe.");
        }
        
        // 2. Mapear DTO a Entidad
        PagoSuscripcion pago = new PagoSuscripcion();
        
        // 3. Asignar el objeto Restaurante completo (obtenido de la validación)
        pago.setRestaurante(optRestaurante.get()); 
        
        // 4. Mapear el resto de campos
        pago.setFecha_pago(pagoDTO.getFecha_pago());
        pago.setMonto_pagado(pagoDTO.getMonto_pagado());
        pago.setTipo_suscripcion(pagoDTO.getTipo_suscripcion());
        pago.setFecha_inicio_suscripcion(pagoDTO.getFecha_inicio_suscripcion());
        pago.setFecha_fin_suscripcion(pagoDTO.getFecha_fin_suscripcion());
        pago.setMetodo_pago(pagoDTO.getMetodo_pago());
        pago.setEstado_pago(pagoDTO.getEstado_pago());
        
        // 5. Guardar en la BD
        PagoSuscripcion nuevoPago = pagoRepository.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }
    
    // RF10: Consultar historial de pagos de UN restaurante
    @GetMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<?> obtenerPagosPorRestaurante(@PathVariable Integer idRestaurante) {
        // Validamos si el restaurante existe antes de buscar sus pagos
        if (restauranteRepository.findById(idRestaurante).isEmpty()) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("El restaurante con ID " + idRestaurante + " no existe.");
        }
        
        // Usamos el método del repositorio
        List<PagoSuscripcion> pagos = pagoRepository.findByRestauranteIdRestaurante(idRestaurante);
        return ResponseEntity.ok(pagos);
    }
    
    // (Opcional) Obtener TODOS los pagos de TODOS los restaurantes
    @GetMapping
    public ResponseEntity<List<PagoSuscripcion>> obtenerTodosLosPagos() {
        List<PagoSuscripcion> pagos = pagoRepository.findAll();
        return ResponseEntity.ok(pagos);
    }

    // (Opcional) Obtener un pago específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<PagoSuscripcion> obtenerPagoPorId(@PathVariable Integer id) {
        return pagoRepository.findById(id)
                .map(ResponseEntity::ok) // Si lo encuentra, devuelve 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no, devuelve 404
    }
}