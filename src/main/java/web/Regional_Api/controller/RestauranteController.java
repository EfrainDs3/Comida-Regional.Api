package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.entity.RestauranteDTO;
import web.Regional_Api.service.IRestauranteService;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {

    @Autowired
    private IRestauranteService restauranteService;

    // 1. GET (Todos)
    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> obtenerTodos() {
        List<RestauranteDTO> restaurantes = restauranteService.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(restaurantes);
    }

    // 2. GET (Por ID)
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> obtenerPorId(@PathVariable Integer id) {
        return restauranteService.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST (Crear)
    @PostMapping
    public ResponseEntity<RestauranteDTO> crear(@RequestBody RestauranteDTO dto) {
        // Mapeo simple de DTO a Entidad
        Restaurante restaurante = new Restaurante();
        restaurante.setRazon_social(dto.getRazon_social());
        restaurante.setRuc(dto.getRuc());
        restaurante.setDireccion_principal(dto.getDireccion_principal());
        restaurante.setLogo_url(dto.getLogo_url());
        restaurante.setEmail(dto.getEmail());
        restaurante.setMoneda(dto.getMoneda());
        restaurante.setSimbolo_moneda(dto.getSimbolo_moneda());
        restaurante.setTasa_igv(dto.getTasa_igv());

        Restaurante nuevoRestaurante = restauranteService.guardar(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(nuevoRestaurante));
    }

    // 4. PUT (Actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> actualizar(@PathVariable Integer id, @RequestBody RestauranteDTO dto) {
        
        Optional<Restaurante> opt = restauranteService.buscarId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Restaurante restaurante = opt.get();
        
        // Mapeo simple de DTO a Entidad
        if (dto.getRazon_social() != null) restaurante.setRazon_social(dto.getRazon_social());
        if (dto.getRuc() != null) restaurante.setRuc(dto.getRuc());
        if (dto.getDireccion_principal() != null) restaurante.setDireccion_principal(dto.getDireccion_principal());
        if (dto.getLogo_url() != null) restaurante.setLogo_url(dto.getLogo_url());
        if (dto.getEmail() != null) restaurante.setEmail(dto.getEmail());
        if (dto.getMoneda() != null) restaurante.setMoneda(dto.getMoneda());
        if (dto.getSimbolo_moneda() != null) restaurante.setSimbolo_moneda(dto.getSimbolo_moneda());
        if (dto.getTasa_igv() != null) restaurante.setTasa_igv(dto.getTasa_igv());
        if (dto.getEstado() != null) restaurante.setEstado(dto.getEstado());
        
        Restaurante actualizado = restauranteService.guardar(restaurante);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    // 5. DELETE (Borrado Lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (restauranteService.buscarId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        restauranteService.eliminar(id); 
        return ResponseEntity.noContent().build();
    }

    private RestauranteDTO convertirADTO(Restaurante entidad) {
        RestauranteDTO dto = new RestauranteDTO();
        dto.setId_restaurante(entidad.getId_restaurante());
        dto.setRazon_social(entidad.getRazon_social());
        dto.setRuc(entidad.getRuc());
        dto.setDireccion_principal(entidad.getDireccion_principal());
        dto.setLogo_url(entidad.getLogo_url());
        dto.setMoneda(entidad.getMoneda());
        dto.setSimbolo_moneda(entidad.getSimbolo_moneda());
        dto.setTasa_igv(entidad.getTasa_igv());
        dto.setEstado(entidad.getEstado());
        dto.setEmail(entidad.getEmail());
        dto.setFecha_creacion(entidad.getFecha_creacion());
        return dto;
    }
}