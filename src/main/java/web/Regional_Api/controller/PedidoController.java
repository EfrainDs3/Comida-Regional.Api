package web.Regional_Api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.DetallePedidoDTO;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.entity.PedidoDTO;
import web.Regional_Api.repository.DetallePedidoRepository;
import web.Regional_Api.service.IPedidoService;

@RestController
@RequestMapping("/restful/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;
    
    @Autowired
    private DetallePedidoRepository detalleRepo;

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.buscarTodos());
    }

    // GET: Por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Integer id) {
        return pedidoService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Crear Pedido (Lógica Plana)
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO dto) {
        
        Pedido pedido = new Pedido();
        pedido.setId_mesa(dto.getId_mesa());
        pedido.setId_usuario(dto.getId_usuario());
        pedido.setNotas(dto.getNotas());
        pedido.setEstado_pedido("En preparación");
        
        // 2. Guardar Pedido para obtener el ID generado
        Pedido pedidoGuardado = pedidoService.guardar(pedido);
        Integer idGenerado = pedidoGuardado.getId_pedido();
        
        // 3. Guardar los Detalles manualmente
        if (dto.getDetalles() != null) {
            for (DetallePedidoDTO detDto : dto.getDetalles()) {
                DetallePedido detalle = new DetallePedido();
                
                // Vinculamos manualmente con el ID del padre
                detalle.setId_pedido(idGenerado); 
                
                // Asignamos datos del detalle
                detalle.setId_plato(detDto.getId_plato());
                detalle.setCantidad(detDto.getCantidad());
                detalle.setPrecio_unitario(detDto.getPrecio_unitario());
                
                // Calculamos subtotal
                BigDecimal subtotal = detDto.getPrecio_unitario()
                        .multiply(new BigDecimal(detDto.getCantidad()));
                detalle.setSubtotal(subtotal);
                
                // Guardamos el detalle en la BD
                detalleRepo.save(detalle);
            }
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoGuardado);
    }

    // PUT: Actualizar Estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Integer id, @RequestBody String nuevoEstado) {
        Optional<Pedido> opt = pedidoService.buscarId(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        
        Pedido pedido = opt.get();
        pedido.setEstado_pedido(nuevoEstado);
        return ResponseEntity.ok(pedidoService.guardar(pedido));
    }
}