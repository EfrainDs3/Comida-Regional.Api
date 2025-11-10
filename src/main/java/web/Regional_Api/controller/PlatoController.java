package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.Regional_Api.entity.Categoria;
import web.Regional_Api.entity.Plato;
import web.Regional_Api.entity.PlatoDTO;
// Sucursales relation removed from Plato entity; imports removed
import web.Regional_Api.repository.CategoriaRepository;
import web.Regional_Api.repository.PlatoRepository;

@RestController
@RequestMapping("/api/platos")
@CrossOrigin(origins = "*")
public class PlatoController {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todos los platos
    @GetMapping
    public ResponseEntity<List<Plato>> obtenerTodos() {
        return ResponseEntity.ok(platoRepository.findAll());
    }

    // Obtener plato por ID
    @GetMapping("/{id}")
    public ResponseEntity<Plato> obtenerPorId(@PathVariable Integer id) {
        Optional<Plato> plato = platoRepository.findById(id);
        return plato.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener platos por categoría
    @GetMapping("/categoria/{id_categoria}")
    public ResponseEntity<List<Plato>> obtenerPorCategoria(@PathVariable Integer id_categoria) {
        return ResponseEntity.ok(platoRepository.buscarPorCategoria(id_categoria));
    }

    // Obtener platos disponibles por categoría
    @GetMapping("/categoria/{id_categoria}/disponibles")
    public ResponseEntity<List<Plato>> obtenerDisponiblesPorCategoria(@PathVariable Integer id_categoria) {
        return ResponseEntity.ok(platoRepository.platosDisponiblesPorCategoria(id_categoria));
    }

    // NOTE: The Plato entity does not have a sucursales relation.
    // Endpoints that filtered by sucursal were removed to match the entity model.

    // Buscar platos por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Plato>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(platoRepository.buscarPorNombre(nombre));
    }

    // Crear plato
    @PostMapping
    public ResponseEntity<Plato> crear(@RequestBody PlatoDTO platoDTO) {
        try {
            if (platoDTO.getId_categoria() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Optional<Categoria> categoriaOpt = categoriaRepository.findById(platoDTO.getId_categoria());

            if (categoriaOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Plato plato = new Plato();
            plato.setNombre(platoDTO.getNombre());
            plato.setDescripcion(platoDTO.getDescripcion());
            plato.setPrecio(platoDTO.getPrecio());
            plato.setImagen(platoDTO.getImagen_url());
            plato.setCategoria(categoriaOpt.get());
            // Note: Plato does not reference Sucursales in the current model
            plato.setEstado(1);

            Plato guardado = platoRepository.save(plato);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar plato
    @PutMapping("/{id}")
    public ResponseEntity<Plato> actualizar(@PathVariable Integer id, @RequestBody PlatoDTO platoDTO) {
        Optional<Plato> optional = platoRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Plato plato = optional.get();
        if (platoDTO.getNombre() != null) plato.setNombre(platoDTO.getNombre());
        if (platoDTO.getDescripcion() != null) plato.setDescripcion(platoDTO.getDescripcion());
        if (platoDTO.getPrecio() != null) plato.setPrecio(platoDTO.getPrecio());
        if (platoDTO.getImagen_url() != null) plato.setImagen(platoDTO.getImagen_url());

        Plato actualizado = platoRepository.save(plato);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar plato (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Plato> optional = platoRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Plato plato = optional.get();
        plato.setEstado(0);
        platoRepository.save(plato);
        return ResponseEntity.noContent().build();
    }
}
