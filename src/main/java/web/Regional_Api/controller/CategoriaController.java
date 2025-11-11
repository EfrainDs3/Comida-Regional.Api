package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.Regional_Api.entity.Categoria;
import web.Regional_Api.entity.CategoriaDTO;
import web.Regional_Api.service.ICategoriaService;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

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

    // Crear categoría
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaDTO categoriaDTO) {
        if (categoriaService.buscarPorNombre(categoriaDTO.getNombre()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: La categoría '" + categoriaDTO.getNombre() + "' ya existe.");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria guardada = categoriaService.guardar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(guardada));
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        Optional<Categoria> optional = categoriaService.buscarId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categoria categoria = optional.get();
        if (categoriaDTO.getNombre() != null) categoria.setNombre(categoriaDTO.getNombre());
        if (categoriaDTO.getDescripcion() != null) categoria.setDescripcion(categoriaDTO.getDescripcion());

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
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setEstado(entidad.getEstado());
        return dto;
    }
}
