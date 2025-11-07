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

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.DetallePedidoDTO;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.entity.Plato;
import web.Regional_Api.repository.DetallePedidoRepository;
import web.Regional_Api.repository.PedidoRepository;
import web.Regional_Api.repository.PlatoRepository;

@RestController
@RequestMapping("/api/detalles-pedido")
@CrossOrigin(origins = "*")
public class DetallePedidoController {
    
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private PlatoRepository platoRepository;
    
    // Obtener todos los detalles
    @GetMapping
    public ResponseEntity<List<DetallePedido>> obtenerTodos() {
        List<DetallePedido> detalles = detallePedidoRepository.findAll();
        return ResponseEntity.ok(detalles);
    }
    
    // Obtener detalles por pedido
    @GetMapping("/pedido/{id_pedido}")
    public ResponseEntity<List<DetallePedido>> obtenerPorPedido(@PathVariable Integer id_pedido) {
        List<DetallePedido> detalles = detallePedidoRepository.detallesPorPedido(id_pedido);
        return ResponseEntity.ok(detalles);
    }
    
    // Obtener detalle por ID
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerPorId(@PathVariable Integer id) {
        Optional<DetallePedido> detalle = detallePedidoRepository.findById(id);
        return detalle.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Crear detalle de pedido
    @PostMapping
    public ResponseEntity<DetallePedido> crear(@RequestBody DetallePedidoDTO detalleDTO) {
        try {
            Optional<Pedido> pedido = pedidoRepository.findById(detalleDTO.getId_pedido());
            Optional<Plato> plato = platoRepository.findById(detalleDTO.getId_plato());
            
            if (pedido.isEmpty() || plato.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            DetallePedido detalle = new DetallePedido();
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecio_unitario(detalleDTO.getPrecio_unitario());
            detalle.setSubtotal(detalleDTO.getSubtotal());
            detalle.setObservaciones(detalleDTO.getObservaciones());
            detalle.setId_pedido(pedido.get());
            detalle.setId_plato(plato.get());
            detalle.setEstado(1);
            
            DetallePedido guardado = detallePedidoRepository.save(detalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Actualizar detalle
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizar(@PathVariable Integer id, @RequestBody DetallePedidoDTO detalleDTO) {
        Optional<DetallePedido> optional = detallePedidoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        DetallePedido detalle = optional.get();
        if (detalleDTO.getCantidad() != null) detalle.setCantidad(detalleDTO.getCantidad());
        if (detalleDTO.getPrecio_unitario() != null) detalle.setPrecio_unitario(detalleDTO.getPrecio_unitario());
        if (detalleDTO.getSubtotal() != null) detalle.setSubtotal(detalleDTO.getSubtotal());
        if (detalleDTO.getObservaciones() != null) detalle.setObservaciones(detalleDTO.getObservaciones());
        
        DetallePedido actualizado = detallePedidoRepository.save(detalle);
        return ResponseEntity.ok(actualizado);
    }
    
    // Eliminar detalle (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<DetallePedido> optional = detallePedidoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        DetallePedido detalle = optional.get();
        detalle.setEstado(0);
        detallePedidoRepository.save(detalle);
        return ResponseEntity.noContent().build();
    }
}

