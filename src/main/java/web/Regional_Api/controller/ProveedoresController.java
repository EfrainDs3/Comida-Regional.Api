package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.ProveedoresDTO;
import web.Regional_Api.entity.Proveedores;
import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.repository.RestauranteRepository; 
import web.Regional_Api.service.IProveedoresService;

@RestController
@RequestMapping("/regional/proveedores") 
public class ProveedoresController {

    @Autowired
    private IProveedoresService serviceProveedores;
    
    @Autowired
    private RestauranteRepository repoRestaurante; 
   
    @GetMapping
    public List<ProveedoresDTO> buscarTodos() {
        return serviceProveedores.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedoresDTO> buscarPorId(@PathVariable Integer id) {
        return serviceProveedores.buscarId(id)
                .map(this::convertirADTO)          
                .map(ResponseEntity::ok)           
                .orElse(ResponseEntity.notFound().build()); 
    }

    @PostMapping
    public ResponseEntity<ProveedoresDTO> guardar(@RequestBody ProveedoresDTO dto) {
        try {
            Proveedores entidad = convertirAEntidad(dto);
            Proveedores entidadGuardada = serviceProveedores.guardar(entidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(entidadGuardada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); 
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProveedoresDTO> modificar(@PathVariable Integer id, @RequestBody ProveedoresDTO dto) {
        
        // 1. Buscamos el proveedor por ID
        return serviceProveedores.buscarId(id).map(entidadExistente -> {
            try {
                // --- ¡ESTA ES LA PARTE QUE FALTABA! ---
                // 2. Actualizamos la entidad con los datos del DTO
                entidadExistente.setNombre(dto.getNombre());
                entidadExistente.setRuc(dto.getRuc());
                entidadExistente.setContacto_nombre(dto.getContacto_nombre());
                entidadExistente.setContacto_telefono(dto.getContacto_telefono());
                entidadExistente.setEstado(dto.getEstado());

                // 3. (Opcional) Actualizar también el restaurante si se envía
                if (dto.getIdRestaurante() != null) {
                    Restaurante restaurante = repoRestaurante.findById(dto.getIdRestaurante())
                            .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
                    entidadExistente.setId_restaurante(restaurante);
                }
                // --- FIN DE LA CORRECCIÓN ---

                // 4. Guardamos la entidad YA ACTUALIZADA
                Proveedores entidadActualizada = serviceProveedores.modificar(entidadExistente);
                return ResponseEntity.ok(convertirADTO(entidadActualizada));
            
            } catch (RuntimeException e) {
                 return ResponseEntity.badRequest().<ProveedoresDTO>build();
            }
        }).orElse(ResponseEntity.notFound().<ProveedoresDTO>build()); // 404 si no existe
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        
        Optional<Proveedores> optionalProveedor = serviceProveedores.buscarId(id);
        
        if (optionalProveedor.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }   
        serviceProveedores.eliminar(id);
        
        return ResponseEntity.noContent().build();
    }


    private ProveedoresDTO convertirADTO(Proveedores entidad) {
        ProveedoresDTO dto = new ProveedoresDTO();
        dto.setId_proveedor(entidad.getId_proveedor());
        dto.setNombre(entidad.getNombre());
        dto.setRuc(entidad.getRuc());
        dto.setContacto_nombre(entidad.getContacto_nombre());
        dto.setContacto_telefono(entidad.getContacto_telefono());
        dto.setEstado(entidad.getEstado());
        
        if (entidad.getId_restaurante() != null) {
            dto.setIdRestaurante(entidad.getId_restaurante().getId_restaurante());
        }
        return dto;
    }

    private Proveedores convertirAEntidad(ProveedoresDTO dto) {
        Proveedores entidad = new Proveedores();
        
        entidad.setId_proveedor(dto.getId_proveedor()); 
        entidad.setNombre(dto.getNombre());
        entidad.setRuc(dto.getRuc());
        entidad.setContacto_nombre(dto.getContacto_nombre());
        entidad.setContacto_telefono(dto.getContacto_telefono());
        entidad.setEstado(dto.getEstado() != null ? dto.getEstado() : 1); 

        if (dto.getIdRestaurante() != null) {
            Restaurante restaurante = repoRestaurante.findById(dto.getIdRestaurante())
                    .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " + dto.getIdRestaurante()));
            
            entidad.setId_restaurante(restaurante); 
        } else {
             throw new RuntimeException("Es obligatorio proveer un idRestaurante");
        }
        return entidad;
    }
}