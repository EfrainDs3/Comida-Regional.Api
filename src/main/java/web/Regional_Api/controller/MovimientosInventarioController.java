package web.Regional_Api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import web.Regional_Api.entity.MovimientosInventarioDTO;
import web.Regional_Api.entity.Insumos;
import web.Regional_Api.entity.MovimientosInventario;
import web.Regional_Api.entity.Proveedores;
import web.Regional_Api.entity.Usuarios;

import web.Regional_Api.entity.MovimientosInventario.TipoMovimiento; 
import web.Regional_Api.repository.InsumosRepository; 
import web.Regional_Api.repository.ProveedoresRepository; 
import web.Regional_Api.repository.UsuarioRepository; 
import web.Regional_Api.service.IMovimientosInventarioService;

@RestController
@RequestMapping("/regional/movimientos-inventario")
public class MovimientosInventarioController {

    @Autowired
    private IMovimientosInventarioService serviceMovimientos;

    
    @Autowired private InsumosRepository repoInsumos;
    @Autowired private UsuarioRepository repoUsuario;
    @Autowired private ProveedoresRepository repoProveedores;


    @GetMapping
    public List<MovimientosInventarioDTO> buscarTodos() {
        return serviceMovimientos.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

 
    @GetMapping("/{id}")
    public ResponseEntity<MovimientosInventarioDTO> buscarPorId(@PathVariable Integer id) {
        return serviceMovimientos.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

  
    @PostMapping
    public ResponseEntity<MovimientosInventarioDTO> guardar(@RequestBody MovimientosInventarioDTO dto) {
        try {
            MovimientosInventario entidad = convertirAEntidad(dto);
            MovimientosInventario entidadGuardada = serviceMovimientos.guardar(entidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(entidadGuardada));
        
        } catch (RuntimeException e) {
            
            return ResponseEntity.badRequest().body(null); 
        }
    }
    
  
    private MovimientosInventarioDTO convertirADTO(MovimientosInventario entidad) {
        MovimientosInventarioDTO dto = new MovimientosInventarioDTO();
        dto.setId_movimiento(entidad.getId_movimiento());
        dto.setCantidad(entidad.getCantidad());
        dto.setMotivo(entidad.getMotivo());
        dto.setTipo_movimiento(entidad.getTipo_movimiento().name()); // Enum a String
        dto.setFecha_movimiento(entidad.getFecha_movimiento());
        
        dto.setIdInsumo(entidad.getInsumo().getId_insumo());
        dto.setIdUsuario(entidad.getUsuario().getIdUsuario()); // Asumiendo .getId_usuario()
        if (entidad.getProveedor() != null) {
            dto.setIdProveedor(entidad.getProveedor().getId_proveedor());
        }
        return dto;
    }

    private MovimientosInventario convertirAEntidad(MovimientosInventarioDTO dto) {
        MovimientosInventario entidad = new MovimientosInventario();
        
        entidad.setCantidad(dto.getCantidad());
        entidad.setMotivo(dto.getMotivo());
        
      
        entidad.setTipo_movimiento(TipoMovimiento.valueOf(dto.getTipo_movimiento()));

        Insumos insumo = repoInsumos.findById(dto.getIdInsumo())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));
        entidad.setInsumo(insumo);

     
        Usuarios usuario = repoUsuario.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        entidad.setUsuario(usuario);

  
        if (dto.getIdProveedor() != null) {
            Proveedores proveedor = repoProveedores.findById(dto.getIdProveedor())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            entidad.setProveedor(proveedor);
        }

        if (entidad.getTipo_movimiento() == TipoMovimiento.Entrada && entidad.getProveedor() == null) {
            throw new RuntimeException("Se requiere un proveedor para los movimientos de 'Entrada'");
        }

        return entidad;
    }
}
