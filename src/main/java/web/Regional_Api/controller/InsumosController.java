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
import web.Regional_Api.entity.Sucursales;
import web.Regional_Api.repository.SucursalesRepository;
import web.Regional_Api.entity.Insumos;

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

    @GetMapping("/{id}")
    public ResponseEntity<InsumosDTO> buscarPorId(@PathVariable Integer id) {
        return serviceInsumos.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InsumosDTO> guardar(@RequestBody InsumosDTO dto) {
        try {
            Insumos entidad = convertirAEntidad(dto);
            Insumos entidadGuardada = serviceInsumos.guardar(entidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(entidadGuardada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumosDTO> modificar(@PathVariable Integer id, @RequestBody InsumosDTO dto) {

        Optional<Insumos> optInsumo = serviceInsumos.buscarId(id);

        if (optInsumo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Insumos entidadExistente = optInsumo.get();

            entidadExistente.setNombre(dto.getNombre());
            entidadExistente.setDescripcion(dto.getDescripcion());
            entidadExistente.setStock_actual(dto.getStock_actual());
            entidadExistente.setStock_minimo(dto.getStock_minimo());
            entidadExistente.setUnidad_medida(dto.getUnidad_medida());
            entidadExistente.setFecha_vencimiento(dto.getFecha_vencimiento());
            entidadExistente.setEstado(dto.getEstado());

            Sucursales sucursal = repoSucursal.findById(dto.getIdSucursal())
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            entidadExistente.setSucursales(sucursal);

            Insumos entidadActualizada = serviceInsumos.modificar(entidadExistente);
            return ResponseEntity.ok(convertirADTO(entidadActualizada));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Insumos> optionalInsumo = serviceInsumos.buscarId(id);

        if (optionalInsumo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        serviceInsumos.eliminar(id);
        return ResponseEntity.noContent().build();
    }

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

        if (entidad.getSucursales() != null) {
            dto.setIdSucursal(entidad.getSucursales().getIdSucursal());
        }
        return dto;
    }

    private Insumos convertirAEntidad(InsumosDTO dto) {
        Insumos entidad = new Insumos();
        entidad.setId_insumo(dto.getId_insumo());
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setStock_actual(dto.getStock_actual());
        entidad.setStock_minimo(dto.getStock_minimo());
        entidad.setUnidad_medida(dto.getUnidad_medida());
        entidad.setFecha_vencimiento(dto.getFecha_vencimiento());
        entidad.setEstado(dto.getEstado() != null ? dto.getEstado() : 1);

        if (dto.getIdSucursal() != null) {
            Sucursales sucursal = repoSucursal.findById(dto.getIdSucursal())
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + dto.getIdSucursal()));
            entidad.setSucursales(sucursal);
        } else {
            throw new RuntimeException("Es obligatorio proveer un idSucursal");
        }
        return entidad;
    }
}
