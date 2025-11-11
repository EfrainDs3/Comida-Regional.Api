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
import web.Regional_Api.service.ICategoriaService; 

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    
    // ❌ SE REEMPLAZA ESTA LÍNEA
    // @Autowired
    // private CategoriaRepository categoriaRepository;
    
    // ✅ POR ESTA LÍNEA
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
        // La lógica del Optional se maneja mejor en el servicio
        return categoriaService.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Crear categoría
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaDTO categoriaDTO) {
        // Validamos si ya existe (lógica de negocio que SÍ va aquí)
        if (categoriaService.buscarPorNombre(categoriaDTO.getNombre()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("Error: La categoría '" + categoriaDTO.getNombre() + "' ya existe.");
        }
        
        // Mapeo DTO a Entidad
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        // El estado por defecto es 1 (definido en tu Entidad)
        
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

    private CategoriaDTO convertirADTO(Categoria entidad) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId_categoria(entidad.getId_categoria());
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setEstado(entidad.getEstado());
        return dto;
    }
}