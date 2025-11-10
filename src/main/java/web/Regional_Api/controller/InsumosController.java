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

import web.Regional_Api.entity.InsumosDTO;
import web.Regional_Api.entity.Insumos;
import web.Regional_Api.entity.Sucursal;
import web.Regional_Api.repository.SucursalRepository; 
import web.Regional_Api.service.IInsumosService;

@RestController
@RequestMapping("/regional/insumos")
public class InsumosController {

    @Autowired
    private IInsumosService serviceInsumos;


    @Autowired
    private SucursalesRepository repoSucursal;


    @GetMapping
    public List<InsumosDTO> buscarTodos() {
        return serviceInsumos.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * GET /regional/insumos/{id}
     * Busca un insumo por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InsumosDTO> buscarPorId(@PathVariable Integer id) {
        return serviceInsumos.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /regional/insumos
     * Crea un nuevo insumo.
     */
    @PostMapping
    public ResponseEntity<InsumosDTO> guardar(@RequestBody InsumosDTO dto) {
        try {
            Insumos entidad = convertirAEntidad(dto);
            Insumos entidadGuardada = serviceInsumos.guardar(entidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(entidadGuardada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 si la sucursal no existe
        }
    }

    /**
     * PUT /regional/insumos/{id}
     * Modifica un insumo existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InsumosDTO> modificar(@PathVariable Integer id, @RequestBody InsumosDTO dto) {
        
        Optional<Insumos> optInsumo = serviceInsumos.buscarId(id);
        
        if (optInsumo.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }
        
        try {
            Insumos entidadExistente = optInsumo.get();
            
            // Actualizamos la entidad con los datos del DTO
            entidadExistente.setNombre(dto.getNombre());
            entidadExistente.setDescripcion(dto.getDescripcion());
            entidadExistente.setStock_actual(dto.getStock_actual());
            entidadExistente.setStock_minimo(dto.getStock_minimo());
            entidadExistente.setUnidad_medida(dto.getUnidad_medida());
            entidadExistente.setFecha_vencimiento(dto.getFecha_vencimiento());
            entidadExistente.setEstado(dto.getEstado());

            // Buscamos y asignamos la sucursal
            Sucursal sucursal = repoSucursal.findById(dto.getIdSucursal())
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            entidadExistente.setSucursal(sucursal);

            Insumos entidadActualizada = serviceInsumos.modificar(entidadExistente);
            return ResponseEntity.ok(convertirADTO(entidadActualizada));
            
        } catch (RuntimeException e) {
             return ResponseEntity.badRequest().build(); // 400 si la sucursal es inválida
        }
    }

    /**
     * DELETE /regional/insumos/{id}
     * Elimina (lógicamente) un insumo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Insumos> optionalInsumo = serviceInsumos.buscarId(id);
        
        if (optionalInsumo.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }
        
        serviceInsumos.eliminar(id); // Llama al borrado lógico
        return ResponseEntity.noContent().build(); // 204
    }

    // --- Métodos de Mapeo (Helpers) ---

    private InsumosDTO convertirADTO(Insumos entidad) {
        InsumosDTO dto = new InsumosDTO();
        dto.setId_insumo(entidad.getId_insumo());
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setStock_actual(entidad.getStock_actual());
        dto.setStock_minimo(entidad.getStock_minimo());
        dto.setUnidad_medida(entidad.getUnidad_medida());
        dto.setFecha_vencimiento(entidad.getFecha_vencimiento());
        dto.setEstado(entidad.getEstado());
        
        if (entidad.getSucursal() != null) {
            dto.setIdSucursal(entidad.getSucursal().getId_sucursal()); // Asumiendo que el ID se llama 'id_sucursal'
        }
        return dto;
    }

    private Insumos convertirAEntidad(InsumosDTO dto) {
        Insumos entidad = new Insumos();
        entidad.setId_insumo(dto.getId_insumo()); // Será null si es nuevo
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setStock_actual(dto.getStock_actual());
        entidad.setStock_minimo(dto.getStock_minimo());
        entidad.setUnidad_medida(dto.getUnidad_medida());
        entidad.setFecha_vencimiento(dto.getFecha_vencimiento());
        entidad.setEstado(dto.getEstado() != null ? dto.getEstado() : 1);

        // Buscamos la sucursal completa
        if (dto.getIdSucursal() != null) {
            Sucursal sucursal = repoSucursal.findById(dto.getIdSucursal())
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + dto.getIdSucursal()));
            entidad.setSucursal(sucursal);
        } else {
             throw new RuntimeException("Es obligatorio proveer un idSucursal");
        }
        return entidad;
    }
}
