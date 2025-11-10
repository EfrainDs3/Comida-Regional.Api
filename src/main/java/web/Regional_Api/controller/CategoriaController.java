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
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        // Ahora llamas al servicio
        List<Categoria> categorias = categoriaService.buscarTodos(); 
        return ResponseEntity.ok(categorias);
    }
    
    // Obtener categorías activas
    @GetMapping("/activas")
    public ResponseEntity<List<Categoria>> obtenerActivas() {
        // Tu servicio ya tiene este método
        List<Categoria> categorias = categoriaService.buscarActivas();
        return ResponseEntity.ok(categorias);
    }
    
    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Integer id) {
        // La lógica del Optional se maneja mejor en el servicio
        return categoriaService.buscarId(id)
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
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }
    
    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        Optional<Categoria> optional = categoriaService.buscarId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Categoria categoria = optional.get();
        if (categoriaDTO.getNombre() != null) categoria.setNombre(categoriaDTO.getNombre());
        if (categoriaDTO.getDescripcion() != null) categoria.setDescripcion(categoriaDTO.getDescripcion());
        
        Categoria actualizada = categoriaService.modificar(categoria);
        return ResponseEntity.ok(actualizada);
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
}
/*
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

*/