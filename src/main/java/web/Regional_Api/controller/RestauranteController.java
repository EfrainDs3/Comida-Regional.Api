package web.Regional_Api.controller;

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

import web.Regional_Api.entity.RestauranteDTO;
import web.Regional_Api.service.RestauranteService;
@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {
    
    @Autowired
    private RestauranteService restauranteService;
    
    /**
     * Obtener todos los restaurantes
     */
    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> obtenerTodos() {
        List<RestauranteDTO> restaurantes = restauranteService.obtenerTodos();
        return ResponseEntity.ok(restaurantes);
    }
    
    /**
     * Obtener restaurante por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> obtenerPorId(@PathVariable Integer id) {
        RestauranteDTO restaurante = restauranteService.obtenerPorId(id);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Obtener restaurante por RUC
     */
    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<RestauranteDTO> obtenerPorRuc(@PathVariable String ruc) {
        RestauranteDTO restaurante = restauranteService.obtenerPorRuc(ruc);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Buscar restaurantes por nombre
     */
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<RestauranteDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<RestauranteDTO> restaurantes = restauranteService.buscarPorNombre(nombre);
        return ResponseEntity.ok(restaurantes);
    }
    
    /**
     * Obtener restaurantes activos
     */
    @GetMapping("/filtro/activos")
    public ResponseEntity<List<RestauranteDTO>> obtenerActivos() {
        List<RestauranteDTO> restaurantes = restauranteService.obtenerActivos();
        return ResponseEntity.ok(restaurantes);
    }
    
    /**
     * Obtener restaurantes con suscripción vencida
     */
    @GetMapping("/filtro/vencidos")
    public ResponseEntity<List<RestauranteDTO>> obtenerConSuscripcionVencida() {
        List<RestauranteDTO> restaurantes = restauranteService.obtenerConSuscripcionVencida();
        return ResponseEntity.ok(restaurantes);
    }
    
    /**
     * Obtener restaurantes con suscripción próxima a vencer
     */
    @GetMapping("/filtro/proximo-vencimiento")
    public ResponseEntity<List<RestauranteDTO>> obtenerProximoVencimiento(@RequestParam Integer dias) {
        List<RestauranteDTO> restaurantes = restauranteService.obtenerProximoVencimiento(dias);
        return ResponseEntity.ok(restaurantes);
    }
    
    /**
     * Crear nuevo restaurante
     */
    @PostMapping
    public ResponseEntity<RestauranteDTO> crearRestaurante(@RequestBody RestauranteDTO dto) {
        try {
            RestauranteDTO restaurante = restauranteService.crearRestaurante(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Actualizar restaurante
     */
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> actualizarRestaurante(
            @PathVariable Integer id,
            @RequestBody RestauranteDTO dto) {
        try {
            RestauranteDTO restaurante = restauranteService.actualizarRestaurante(id, dto);
            return ResponseEntity.ok(restaurante);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Cambiar estado del restaurante
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<RestauranteDTO> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam Integer estado) {
        try {
            RestauranteDTO restaurante = restauranteService.cambiarEstado(id, estado);
            return ResponseEntity.ok(restaurante);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Renovar suscripción del restaurante
     */
    @PutMapping("/{id}/renovar-suscripcion")
    public ResponseEntity<RestauranteDTO> renovarSuscripcion(
            @PathVariable Integer id,
            @RequestParam Integer dias) {
        try {
            RestauranteDTO restaurante = restauranteService.renovarSuscripcion(id, dias);
            return ResponseEntity.ok(restaurante);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Eliminar restaurante
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRestaurante(@PathVariable Integer id) {
        try {
            restauranteService.eliminarRestaurante(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

