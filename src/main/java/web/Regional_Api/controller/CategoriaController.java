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

import web.Regional_Api.entity.Categoria;
import web.Regional_Api.entity.CategoriaDTO;
import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.entity.Sucursales;
import web.Regional_Api.service.ICategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;
    @Autowired
    private web.Regional_Api.repository.CategoriaRepository categoriaRepository;
    @Autowired
    private web.Regional_Api.repository.RestauranteRepository restauranteRepository;
    @Autowired
    private web.Regional_Api.repository.SucursalesRepository sucursalesRepository;

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obtenerTodas() {
        List<CategoriaDTO> categorias = categoriaService.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    // Obtener categorías activas
    @GetMapping("/activas")
    public ResponseEntity<List<CategoriaDTO>> obtenerActivas() {
        List<CategoriaDTO> categorias = categoriaService.buscarActivas().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Integer id) {
        return categoriaService.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaDTO categoriaDTO) {

        // 1. Validar que el restaurante existe
        Optional<Restaurante> optRestaurante = restauranteRepository.findById(categoriaDTO.getId_restaurante());
        if (optRestaurante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: El restaurante con ID " + categoriaDTO.getId_restaurante() + " no existe.");
        }

        // 2. Validar que la sucursal existe
        Optional<Sucursales> optSucursal = sucursalesRepository.findById(categoriaDTO.getId_sucursal());
        if (optSucursal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: La sucursal con ID " + categoriaDTO.getId_sucursal() + " no existe.");
        }

        // 3. Mapear DTO a Entidad
        Categoria categoria = new Categoria();
        categoria.setRestaurante(optRestaurante.get());
        categoria.setSucursales(optSucursal.get());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setEstado(categoriaDTO.getEstado() != null ? categoriaDTO.getEstado() : 1);

        try {
            Categoria guardada = categoriaRepository.save(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(guardada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar: " + e.getMessage());
        }
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        Optional<Categoria> optional = categoriaService.buscarId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categoria categoria = optional.get();
        if (categoriaDTO.getNombre() != null)
            categoria.setNombre(categoriaDTO.getNombre());
        if (categoriaDTO.getDescripcion() != null)
            categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria actualizada = categoriaService.modificar(categoria);
        return ResponseEntity.ok(convertirADTO(actualizada));
    }

    // Eliminar categoría (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Categoria> optional = categoriaService.buscarId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Conversión Entidad -> DTO
    private CategoriaDTO convertirADTO(Categoria entidad) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId_categoria(entidad.getId_categoria());
        dto.setId_restaurante(entidad.getRestaurante().getIdRestaurante());
        dto.setId_sucursal(entidad.getSucursales().getIdSucursal());
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setEstado(entidad.getEstado());
        return dto;
    }
}