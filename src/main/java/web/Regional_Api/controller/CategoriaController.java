package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import web.Regional_Api.entity.Categoria;
import web.Regional_Api.entity.CategoriaDTO;
import web.Regional_Api.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return ResponseEntity.ok(categorias);
    }
    
    // Obtener categorías activas
    @GetMapping("/activas")
    public ResponseEntity<List<Categoria>> obtenerActivas() {
        List<Categoria> categorias = categoriaRepository.categoriasActivas();
        return ResponseEntity.ok(categorias);
    }
    
    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Integer id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Buscar categorías
    @GetMapping("/buscar")
    public ResponseEntity<List<Categoria>> buscar(@RequestParam String nombre) {
        List<Categoria> categorias = categoriaRepository.findByNombreContainingIgnoreCase(nombre);
        return ResponseEntity.ok(categorias);
    }
    
    // Crear categoría
    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre(categoriaDTO.getNombre());
            categoria.setDescripcion(categoriaDTO.getDescripcion());
            categoria.setEstado(1);
            
            Categoria guardada = categoriaRepository.save(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Categoria categoria = optional.get();
        if (categoriaDTO.getNombre() != null) categoria.setNombre(categoriaDTO.getNombre());
        if (categoriaDTO.getDescripcion() != null) categoria.setDescripcion(categoriaDTO.getDescripcion());
        
        Categoria actualizada = categoriaRepository.save(categoria);
        return ResponseEntity.ok(actualizada);
    }
    
    // Eliminar categoría (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Categoria categoria = optional.get();
        categoria.setEstado(0);
        categoriaRepository.save(categoria);
        return ResponseEntity.noContent().build();
    }
}

