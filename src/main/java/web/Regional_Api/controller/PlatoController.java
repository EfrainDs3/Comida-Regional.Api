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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.Categoria;
import web.Regional_Api.entity.Plato;
import web.Regional_Api.entity.PlatoDTO;
import web.Regional_Api.entity.Sucursales;
import web.Regional_Api.repository.CategoriaRepository;
import web.Regional_Api.repository.PlatoRepository;
import web.Regional_Api.repository.SucursalesRepository;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private SucursalesRepository sucursalesRepository;

    // Obtener todos los platos
    @GetMapping
    public ResponseEntity<List<PlatoDTO>> obtenerTodos() {
        List<PlatoDTO> platos = platoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(platos);
    }

    // Obtener plato por ID
    @GetMapping("/{id}")
    public ResponseEntity<PlatoDTO> obtenerPorId(@PathVariable Integer id) {
        Optional<Plato> plato = platoRepository.findById(id);
        return plato.map(this::convertirADTO)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener platos por categoría
    @GetMapping("/categoria/{id_categoria}")
    public ResponseEntity<List<PlatoDTO>> obtenerPorCategoria(@PathVariable Integer id_categoria) {
        List<PlatoDTO> platos = platoRepository.buscarPorCategoria(id_categoria).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(platos);
    }

    // Obtener platos disponibles por categoría
    @GetMapping("/categoria/{id_categoria}/disponibles")
    public ResponseEntity<List<PlatoDTO>> obtenerDisponiblesPorCategoria(@PathVariable Integer id_categoria) {
        List<PlatoDTO> platos = platoRepository.platosDisponiblesPorCategoria(id_categoria).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(platos);
    }

    // Buscar platos por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<PlatoDTO>> buscar(@RequestParam String nombre) {
        List<PlatoDTO> platos = platoRepository.buscarPorNombre(nombre).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(platos);
    }

    // Crear plato
    @PostMapping
    public ResponseEntity<PlatoDTO> crear(@RequestBody PlatoDTO platoDTO) {
        // Validar que la categoría existe
        if (platoDTO.getId_categoria() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(platoDTO.getId_categoria());
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Validar que la sucursal existe
        if (platoDTO.getId_sucursal() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Sucursales> sucursalOpt = sucursalesRepository.findById(platoDTO.getId_sucursal());
        if (sucursalOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Plato plato = new Plato();
        plato.setNombre(platoDTO.getNombre());
        plato.setDescripcion(platoDTO.getDescripcion());
        plato.setPrecio(platoDTO.getPrecio());
        plato.setCategoria(categoriaOpt.get());
        plato.setSucursales(sucursalOpt.get());
        plato.setEstado(1);

        Plato guardado = platoRepository.save(plato);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(guardado));
    }

    // Actualizar plato
    @PutMapping("/{id}")
    public ResponseEntity<PlatoDTO> actualizar(@PathVariable Integer id, @RequestBody PlatoDTO platoDTO) {
        Optional<Plato> optional = platoRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Plato plato = optional.get();
        if (platoDTO.getNombre() != null) plato.setNombre(platoDTO.getNombre());
        if (platoDTO.getDescripcion() != null) plato.setDescripcion(platoDTO.getDescripcion());
        if (platoDTO.getPrecio() != null) plato.setPrecio(platoDTO.getPrecio());

        Plato actualizado = platoRepository.save(plato);
        return ResponseEntity.ok(convertirADTO(actualizado));
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

    private PlatoDTO convertirADTO(Plato entidad) {
        PlatoDTO dto = new PlatoDTO();
        dto.setId_plato(entidad.getId_plato());
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setPrecio(entidad.getPrecio());
        dto.setEstado(entidad.getEstado());
        
        // Manejar relaciones lazy de forma segura
        if (entidad.getCategoria() != null) {
            dto.setId_categoria(entidad.getCategoria().getId_categoria());
        }
        if (entidad.getSucursales() != null) {
            dto.setId_sucursal(entidad.getSucursales().getIdSucursal());
        }
        
        return dto;
    }
}
