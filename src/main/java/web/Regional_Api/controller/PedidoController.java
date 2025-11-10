package web.Regional_Api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.Regional_Api.dto.DetallePedidoDTO;
import web.Regional_Api.dto.PedidoDTO;

import web.Regional_Api.entity.*; 
import web.Regional_Api.service.IPedidoService;

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
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.buscarTodos());
    }

    // 2. GET (Por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Integer id) {
        return pedidoService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO dto) { // Asumo que usas PedidoDTO
        
        Optional<Sucursal> suc = sucursalRepo.findById(dto.getId_sucursal());
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
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }
    // 4. PUT (Actualizar ESTADO de Pedido)

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Integer id, 
            @RequestBody String nuevoEstado) { 
        
        Optional<Pedido> opt = pedidoService.buscarId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Pedido pedido = opt.get();
        
        pedido.setEstado_pedido(nuevoEstado); 
        
        Pedido actualizado = pedidoService.guardar(pedido);
        return ResponseEntity.ok(actualizado);
    }
    
    // 5. DELETE (No implementado)
    // no podemos borrar físicamente.

}