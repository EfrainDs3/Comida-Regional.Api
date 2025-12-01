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
import web.Regional_Api.entity.RestauranteDTO;
import web.Regional_Api.service.IRestauranteService;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    @Autowired
    private IRestauranteService restauranteService;

    // 1. GET (Todos)
    @GetMapping
    public ResponseEntity<List<Restaurante>> obtenerTodos() {
        List<Restaurante> restaurantes = restauranteService.buscarTodos();
        return ResponseEntity.ok(restaurantes);
    }

    // 2. GET (Por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> obtenerPorId(@PathVariable Integer id) {
        return restauranteService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST (Crear)
    @PostMapping
    public ResponseEntity<Restaurante> crear(@RequestBody RestauranteDTO dto) {
        // Mapeo simple de DTO a Entidad
        Restaurante restaurante = new Restaurante();
        restaurante.setRazon_social(dto.getRazon_social());
        restaurante.setRuc(dto.getRuc());
        restaurante.setDireccion_principal(dto.getDireccion_principal());
        restaurante.setLogo_url(dto.getLogo_url());

        // Los campos con DEFAULT (moneda, igv, estado) se asignan solos
        // gracias a @DynamicInsert en la Entidad.

        Restaurante nuevoRestaurante = restauranteService.guardar(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRestaurante);
    }

    // 4. PUT (Actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> actualizar(@PathVariable Integer id, @RequestBody RestauranteDTO dto) {

        Optional<Restaurante> opt = restauranteService.buscarId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Restaurante restaurante = opt.get();

        // Mapeo simple de DTO a Entidad
        restaurante.setRazon_social(dto.getRazon_social());
        restaurante.setRuc(dto.getRuc());
        restaurante.setDireccion_principal(dto.getDireccion_principal());
        restaurante.setLogo_url(dto.getLogo_url());
        restaurante.setMoneda(dto.getMoneda());
        restaurante.setSimbolo_moneda(dto.getSimbolo_moneda());
        restaurante.setTasa_igv(dto.getTasa_igv());

        Restaurante actualizado = restauranteService.guardar(restaurante);
        return ResponseEntity.ok(actualizado);
    }

    // 5. DELETE (Borrado Lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (restauranteService.buscarId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        restauranteService.eliminar(id); // Esto activa el @SQLDelete
        return ResponseEntity.noContent().build();
    }
}