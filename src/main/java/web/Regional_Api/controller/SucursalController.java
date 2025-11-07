package web.Regional_Api.controller;

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
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.entity.Sucursal;
import web.Regional_Api.entity.SucursalDTO;
import web.Regional_Api.repository.RestauranteRepository;
import web.Regional_Api.repository.SucursalRepository;

@RestController
@RequestMapping("/api/sucursales")
@CrossOrigin(origins = "*")
public class SucursalController {
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    // Obtener todas las sucursales
    @GetMapping
    public ResponseEntity<List<Sucursal>> obtenerTodas() {
        List<Sucursal> sucursales = sucursalRepository.findAll();
        return ResponseEntity.ok(sucursales);
    }
    
    // Obtener sucursal por ID
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerPorId(@PathVariable Integer id) {
        Optional<Sucursal> sucursal = sucursalRepository.findById(id);
        return sucursal.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Obtener sucursales por restaurante
    @GetMapping("/restaurante/{id_restaurante}")
    public ResponseEntity<List<Sucursal>> obtenerPorRestaurante(@PathVariable Integer id_restaurante) {
        List<Sucursal> sucursales = sucursalRepository.findByIdRestaurante_Id_restaurante(id_restaurante);
        return ResponseEntity.ok(sucursales);
    }
    
    // Obtener sucursales activas por restaurante
    @GetMapping("/restaurante/{id_restaurante}/activas")
    public ResponseEntity<List<Sucursal>> obtenerActivasPorRestaurante(@PathVariable Integer id_restaurante) {
        List<Sucursal> sucursales = sucursalRepository.sucursalesActivasPorRestaurante(id_restaurante);
        return ResponseEntity.ok(sucursales);
    }
    
    // Crear nueva sucursal
    @PostMapping
    public ResponseEntity<Sucursal> crear(@RequestBody SucursalDTO sucursalDTO) {
        try {
            Optional<Restaurante> restaurante = restauranteRepository.findById(sucursalDTO.getId_restaurante());
            if (restaurante.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(sucursalDTO.getNombre());
            sucursal.setDireccion(sucursalDTO.getDireccion());
            sucursal.setTelefono(sucursalDTO.getTelefono());
            sucursal.setCiudad(sucursalDTO.getCiudad());
            sucursal.setEstado_sucursal("Activo");
            sucursal.setId_restaurante(restaurante.get());
            sucursal.setEstado(1);
            
            Sucursal guardada = sucursalRepository.save(sucursal);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Actualizar sucursal
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Integer id, @RequestBody SucursalDTO sucursalDTO) {
        Optional<Sucursal> optional = sucursalRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Sucursal sucursal = optional.get();
        if (sucursalDTO.getNombre() != null) sucursal.setNombre(sucursalDTO.getNombre());
        if (sucursalDTO.getDireccion() != null) sucursal.setDireccion(sucursalDTO.getDireccion());
        if (sucursalDTO.getTelefono() != null) sucursal.setTelefono(sucursalDTO.getTelefono());
        if (sucursalDTO.getCiudad() != null) sucursal.setCiudad(sucursalDTO.getCiudad());
        if (sucursalDTO.getEstado_sucursal() != null) sucursal.setEstado_sucursal(sucursalDTO.getEstado_sucursal());
        
        Sucursal actualizada = sucursalRepository.save(sucursal);
        return ResponseEntity.ok(actualizada);
    }
    
    // Eliminar sucursal (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Sucursal> optional = sucursalRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Sucursal sucursal = optional.get();
        sucursal.setEstado(0);
        sucursalRepository.save(sucursal);
        return ResponseEntity.noContent().build();
    }
}

