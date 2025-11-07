package web.Regional_Api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.Pedido;
import web.Regional_Api.entity.PedidoDTO;
import web.Regional_Api.entity.Sucursal;
import web.Regional_Api.repository.PedidoRepository;
import web.Regional_Api.repository.SucursalRepository;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    // Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }
    
    // Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Obtener pedidos por sucursal
    @GetMapping("/sucursal/{id_sucursal}")
    public ResponseEntity<List<Pedido>> obtenerPorSucursal(@PathVariable Integer id_sucursal) {
        List<Pedido> pedidos = pedidoRepository.findByIdSucursal_Id_sucursal(id_sucursal);
        return ResponseEntity.ok(pedidos);
    }
    
    // Obtener pedidos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPorEstado(@PathVariable String estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstado_pedido(estado);
        return ResponseEntity.ok(pedidos);
    }
    
    // Crear pedido
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody PedidoDTO pedidoDTO) {
        try {
            Optional<Sucursal> sucursal = sucursalRepository.findById(pedidoDTO.getId_sucursal());
            if (sucursal.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Pedido pedido = new Pedido();
            pedido.setNumero_pedido("PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            pedido.setFecha_pedido(LocalDateTime.now());
            pedido.setEstado_pedido("Nuevo");
            pedido.setTotal(pedidoDTO.getTotal());
            pedido.setMesa_numero(pedidoDTO.getMesa_numero());
            pedido.setNotas(pedidoDTO.getNotas());
            pedido.setId_sucursal(sucursal.get());
            pedido.setEstado(1);
            
            Pedido guardado = pedidoRepository.save(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Actualizar estado del pedido
    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Integer id, @RequestParam String estado_pedido) {
        Optional<Pedido> optional = pedidoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Pedido pedido = optional.get();
        pedido.setEstado_pedido(estado_pedido);
        Pedido actualizado = pedidoRepository.save(pedido);
        return ResponseEntity.ok(actualizado);
    }
    
    // Actualizar pedido completo
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody PedidoDTO pedidoDTO) {
        Optional<Pedido> optional = pedidoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Pedido pedido = optional.get();
        if (pedidoDTO.getTotal() != null) pedido.setTotal(pedidoDTO.getTotal());
        if (pedidoDTO.getMesa_numero() != null) pedido.setMesa_numero(pedidoDTO.getMesa_numero());
        if (pedidoDTO.getNotas() != null) pedido.setNotas(pedidoDTO.getNotas());
        if (pedidoDTO.getEstado_pedido() != null) pedido.setEstado_pedido(pedidoDTO.getEstado_pedido());
        
        Pedido actualizado = pedidoRepository.save(pedido);
        return ResponseEntity.ok(actualizado);
    }
    
    // Eliminar pedido (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Pedido> optional = pedidoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Pedido pedido = optional.get();
        pedido.setEstado(0);
        pedidoRepository.save(pedido);
        return ResponseEntity.noContent().build();
    }
}
