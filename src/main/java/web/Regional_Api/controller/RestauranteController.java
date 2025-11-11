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

    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> obtenerTodos() {
        List<RestauranteDTO> lista = restauranteService.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> obtenerPorId(@PathVariable Integer id) {
        return restauranteService.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RestauranteDTO dto) {
        if (dto.getRazon_social() == null || dto.getRazon_social().isBlank() ||
            dto.getRuc() == null || dto.getRuc().isBlank() ||
            dto.getEmail() == null || dto.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Razon social, RUC y Email son obligatorios.");
        }

        Restaurante r = new Restaurante();
        r.setRazon_social(dto.getRazon_social());
        r.setRuc(dto.getRuc());
        r.setDireccion_principal(dto.getDireccion_principal());
        r.setLogo_url(dto.getLogo_url());
        r.setMoneda(dto.getMoneda());
        r.setSimbolo_moneda(dto.getSimbolo_moneda());
        r.setTasa_igv(dto.getTasa_igv());
        r.setEmail(dto.getEmail());

        Restaurante guardado = restauranteService.guardar(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody RestauranteDTO dto) {
        Optional<Restaurante> opt = restauranteService.buscarId(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Restaurante r = opt.get();
        if (dto.getRazon_social() != null) r.setRazon_social(dto.getRazon_social());
        if (dto.getRuc() != null) r.setRuc(dto.getRuc());
        if (dto.getDireccion_principal() != null) r.setDireccion_principal(dto.getDireccion_principal());
        if (dto.getLogo_url() != null) r.setLogo_url(dto.getLogo_url());
        if (dto.getMoneda() != null) r.setMoneda(dto.getMoneda());
        if (dto.getSimbolo_moneda() != null) r.setSimbolo_moneda(dto.getSimbolo_moneda());
        if (dto.getTasa_igv() != null) r.setTasa_igv(dto.getTasa_igv());
        if (dto.getEmail() != null) r.setEmail(dto.getEmail());

        Restaurante actualizado = restauranteService.guardar(r);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Restaurante> opt = restauranteService.buscarId(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        restauranteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private RestauranteDTO convertirADTO(Restaurante r) {
        RestauranteDTO dto = new RestauranteDTO();
        dto.setRazon_social(r.getRazon_social());
        dto.setRuc(r.getRuc());
        dto.setDireccion_principal(r.getDireccion_principal());
        dto.setLogo_url(r.getLogo_url());
        dto.setMoneda(r.getMoneda());
        dto.setSimbolo_moneda(r.getSimbolo_moneda());
        dto.setTasa_igv(r.getTasa_igv());
        dto.setEmail(r.getEmail());
        return dto;
    }
}
