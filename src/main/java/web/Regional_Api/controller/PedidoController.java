package web.Regional_Api.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired; // Importante para convertir a JSON
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

import com.fasterxml.jackson.databind.ObjectMapper;

import web.Regional_Api.entity.DetallePedidoDTO;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.entity.PedidoDTO;
import web.Regional_Api.service.IPedidoService;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*") // Asegurar acceso desde React
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;
    
    // Jackson Mapper para convertir Objetos <-> Texto JSON
    private final ObjectMapper mapper = new ObjectMapper(); 

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido pedido = new Pedido();
            
            // 1. Datos básicos
            pedido.setId_mesa(pedidoDTO.getId_mesa());
            pedido.setId_usuario(pedidoDTO.getId_usuario());
            pedido.setNotas(pedidoDTO.getNotas());
            pedido.setEstado_pedido("Pendiente");

            // 2. LÓGICA DE DESNORMALIZACIÓN (La magia)
            if (pedidoDTO.getDetalles() != null && !pedidoDTO.getDetalles().isEmpty()) {
                
                // A. Calcular Total sumando la lista en memoria
                BigDecimal totalCalculado = BigDecimal.ZERO;
                for (DetallePedidoDTO d : pedidoDTO.getDetalles()) {
                    BigDecimal sub = d.getPrecio_unitario()
                        .multiply(new BigDecimal(d.getCantidad()));
                    totalCalculado = totalCalculado.add(sub);
                }
                pedido.setTotal(totalCalculado);

                // B. Convertir la Lista de Detalles a un String JSON
                String jsonString = mapper.writeValueAsString(pedidoDTO.getDetalles());
                pedido.setJsonDetalles(jsonString);
            }

            // 3. Guardar solo el Padre (Ya contiene los detalles adentro como texto)
            Pedido nuevoPedido = pedidoService.guardar(pedido);

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error procesando el pedido: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(pedidoService.buscarTodos());
    }

    // PUT para cambiar estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Integer id, @RequestBody String nuevoEstado) {
        return pedidoService.buscarId(id).map(p -> {
            p.setEstado_pedido(nuevoEstado);
            return ResponseEntity.ok(pedidoService.guardar(p));
        }).orElse(ResponseEntity.notFound().build());
    }
}