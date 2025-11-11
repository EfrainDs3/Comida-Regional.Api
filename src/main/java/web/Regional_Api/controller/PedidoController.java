package web.Regional_Api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.Regional_Api.entity.*; 
import web.Regional_Api.service.IPedidoService;

import web.Regional_Api.repository.SucursalesRepository;
import web.Regional_Api.repository.MesasRepository;
import web.Regional_Api.repository.UsuarioRepository;
import web.Regional_Api.repository.PlatoRepository;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    @Autowired
    private SucursalesRepository sucursalRepo;
    @Autowired
    private MesasRepository mesaRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private PlatoRepository platoRepo;

    // 1. GET (Todos)
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> obtenerTodos() {
        List<PedidoDTO> pedidos = pedidoService.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidos);
    }

    // 2. GET (Por ID)
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Integer id) {
        return pedidoService.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO dto) {
        
        Optional<Sucursales> suc = sucursalRepo.findById(dto.getId_sucursal());
        Optional<Mesas> mes = mesaRepo.findById(dto.getId_mesa());
        
        // ✅ LÓGICA CORREGIDA
        Optional<Usuarios> moz = usuarioRepo.findById(dto.getId_usuario_mozo());
        
        if (suc.isEmpty() || mes.isEmpty() || moz.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Sucursal, Mesa o Mozo no encontrado.");
        }
        
        Pedido pedido = new Pedido();
        pedido.setSucursal(suc.get());
        pedido.setMesa(mes.get());
        pedido.setUsuarioMozo(moz.get()); 
        
        BigDecimal totalGeneral = BigDecimal.ZERO;
        List<DetallePedido> detallesEntidad = new ArrayList<>();
        
        for (DetallePedidoDTO detDto : dto.getDetalles()) {
            Optional<Plato> pla = platoRepo.findById(detDto.getId_plato());
            if (pla.isEmpty()) {
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Plato con ID " + detDto.getId_plato() + " no encontrado.");
            }
            
            DetallePedido detalle = new DetallePedido();
            detalle.setPlato(pla.get());
            detalle.setCantidad(detDto.getCantidad());
            detalle.setPrecio_unitario(detDto.getPrecio_unitario());
            detalle.setObservaciones(detDto.getObservaciones());
            
            BigDecimal subtotal = detDto.getPrecio_unitario()
                                    .multiply(new BigDecimal(detDto.getCantidad()));
            detalle.setSubtotal(subtotal);
            
            totalGeneral = totalGeneral.add(subtotal);
            
            detalle.setPedido(pedido);
            detallesEntidad.add(detalle);
        }
        
        pedido.setDetalles(detallesEntidad); 
        pedido.setTotal_pedido(totalGeneral);
        
        Pedido nuevoPedido = pedidoService.guardar(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(nuevoPedido));
    }
    // 4. PUT (Actualizar ESTADO de Pedido)

    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstado(
            @PathVariable Integer id, 
            @RequestBody String nuevoEstado) { 
        
        Optional<Pedido> opt = pedidoService.buscarId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Pedido pedido = opt.get();
        
        pedido.setEstado_pedido(nuevoEstado); 
        
    Pedido actualizado = pedidoService.guardar(pedido);
    return ResponseEntity.ok(convertirADTO(actualizado));
    }
    
    // 5. DELETE (No implementado)
    // no podemos borrar físicamente.

    private PedidoDTO convertirADTO(Pedido entidad) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId_pedido(entidad.getId_pedido());
        dto.setId_sucursal(entidad.getSucursal() != null ? entidad.getSucursal().getIdSucursal() : null);
        dto.setId_mesa(entidad.getMesa() != null ? entidad.getMesa().getId_mesa() : null);
        dto.setId_usuario_mozo(entidad.getUsuarioMozo() != null ? entidad.getUsuarioMozo().getIdUsuario() : null);
        dto.setFecha_hora_pedido(entidad.getFecha_hora_pedido());
        dto.setEstado_pedido(entidad.getEstado_pedido());
        dto.setTotal_pedido(entidad.getTotal_pedido());

        List<DetallePedidoDTO> detalles = entidad.getDetalles() == null ? List.of()
                : entidad.getDetalles().stream()
                        .map(this::convertirDetalle)
                        .collect(Collectors.toList());
        dto.setDetalles(detalles);
        return dto;
    }

    private DetallePedidoDTO convertirDetalle(DetallePedido detalle) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId_detalle(detalle.getId_detalle());
        dto.setId_pedido(detalle.getPedido() != null ? detalle.getPedido().getId_pedido() : null);
        dto.setId_plato(detalle.getPlato() != null ? detalle.getPlato().getId_plato() : null);
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecio_unitario(detalle.getPrecio_unitario());
        dto.setSubtotal(detalle.getSubtotal());
        dto.setObservaciones(detalle.getObservaciones());
        return dto;
    }
}