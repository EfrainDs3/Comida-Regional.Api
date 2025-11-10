package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.DetallePedidoDTO;
import web.Regional_Api.service.IDetallePedidoService;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "*")
public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detalleService;

    // 1. GET (Por ID) - Operación lógica de Lectura
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerPorId(@PathVariable Integer id) {
        return detalleService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 2. PUT (Actualizar) - Operación lógica de Actualización
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizarDetalle(
            @PathVariable Integer id,
            @RequestBody DetallePedidoDTO dto) {
        
        try {
            DetallePedido detalleActualizado = detalleService.actualizar(id, dto);
            return ResponseEntity.ok(detalleActualizado);
        } catch (Exception e) {
            // (Manejo simple de error si no se encuentra)
            return ResponseEntity.notFound().build();
        }
    }

    // 3. DELETE (Eliminar) - Operación lógica de Borrado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer id) {
        try {
            detalleService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // (Manejo simple de error si no se encuentra)
            return ResponseEntity.notFound().build();
        }
    }
    
    // NOTA: No hay POST (Crear) ni GET (Listar Todos)
    // porque son operaciones ilógicas para esta entidad invertebrado.
}
/*
import java.math.BigDecimal;
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
            // Validaciones básicas
            if (detalleDTO.getId_pedido() == null || detalleDTO.getId_plato() == null || 
                detalleDTO.getCantidad() == null || detalleDTO.getPrecio_unitario() == null) {
                return ResponseEntity.badRequest().build();
            }

            Optional<Pedido> pedido = pedidoRepository.findById(detalleDTO.getId_pedido());
            Optional<Plato> plato = platoRepository.findById(detalleDTO.getId_plato());
            
            if (pedido.isEmpty() || plato.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            DetallePedido detalle = new DetallePedido();
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecio_unitario(detalleDTO.getPrecio_unitario());
            // Calcular el subtotal si no viene en el DTO
            if (detalleDTO.getSubtotal() == null) {
                BigDecimal cantidad = BigDecimal.valueOf(detalleDTO.getCantidad());
                detalle.setSubtotal(detalleDTO.getPrecio_unitario().multiply(cantidad));
            } else {
                detalle.setSubtotal(detalleDTO.getSubtotal());
            }
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

*/