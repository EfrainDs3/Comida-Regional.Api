package web.Regional_Api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.entity.RestauranteDTO;
import web.Regional_Api.repository.RestauranteRepository;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    // Obtener todos los restaurantes activos
    @GetMapping
    public ResponseEntity<List<Restaurante>> obtenerTodos() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        return ResponseEntity.ok(restaurantes);
    }
    
    // Obtener restaurante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> obtenerPorId(@PathVariable Integer id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        return restaurante.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Buscar restaurantes
    @GetMapping("/buscar")
    public ResponseEntity<List<Restaurante>> buscar(@RequestParam String search) {
        List<Restaurante> restaurantes = restauranteRepository.buscarRestaurantes(search);
        return ResponseEntity.ok(restaurantes);
    }
    
    // Obtener por estado de pago
    @GetMapping("/estado-pago/{estadoPago}")
    public ResponseEntity<List<Restaurante>> obtenerPorEstadoPago(@PathVariable Integer estadoPago) {
        List<Restaurante> restaurantes = restauranteRepository.findByEstado_pago(estadoPago);
        return ResponseEntity.ok(restaurantes);
    }
    
    // Crear nuevo restaurante
    @PostMapping
    public ResponseEntity<Restaurante> crear(@RequestBody RestauranteDTO restauranteDTO) {
        try {
            // Validar que el RUC no exista
            if (restauranteRepository.findByRuc(restauranteDTO.getRuc()).isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            
            Restaurante restaurante = new Restaurante();
            restaurante.setRazon_social(restauranteDTO.getRazon_social());
            restaurante.setRuc(restauranteDTO.getRuc());
            restaurante.setDireccion(restauranteDTO.getDireccion());
            restaurante.setTelefono(restauranteDTO.getTelefono());
            restaurante.setEmail(restauranteDTO.getEmail());
            restaurante.setHorario_apertura(restauranteDTO.getHorario_apertura());
            restaurante.setHorario_cierre(restauranteDTO.getHorario_cierre());
            restaurante.setFecha_afiliacion(LocalDateTime.now());
            restaurante.setEstado_pago(1); // Al día por defecto
            restaurante.setEstado(1);
            
            Restaurante guardado = restauranteRepository.save(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Actualizar restaurante
    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> actualizar(@PathVariable Integer id, @RequestBody RestauranteDTO restauranteDTO) {
        Optional<Restaurante> optional = restauranteRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Restaurante restaurante = optional.get();
        if (restauranteDTO.getRazon_social() != null) restaurante.setRazon_social(restauranteDTO.getRazon_social());
        if (restauranteDTO.getDireccion() != null) restaurante.setDireccion(restauranteDTO.getDireccion());
        if (restauranteDTO.getTelefono() != null) restaurante.setTelefono(restauranteDTO.getTelefono());
        if (restauranteDTO.getEmail() != null) restaurante.setEmail(restauranteDTO.getEmail());
        if (restauranteDTO.getHorario_apertura() != null) restaurante.setHorario_apertura(restauranteDTO.getHorario_apertura());
        if (restauranteDTO.getHorario_cierre() != null) restaurante.setHorario_cierre(restauranteDTO.getHorario_cierre());
        if (restauranteDTO.getEstado_pago() != null) restaurante.setEstado_pago(restauranteDTO.getEstado_pago());
        
        Restaurante actualizado = restauranteRepository.save(restaurante);
        return ResponseEntity.ok(actualizado);
    }
    
    // Eliminar restaurante (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Restaurante> optional = restauranteRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Restaurante restaurante = optional.get();
        restaurante.setEstado(0);
        restauranteRepository.save(restaurante);
        return ResponseEntity.noContent().build();
    }
}

