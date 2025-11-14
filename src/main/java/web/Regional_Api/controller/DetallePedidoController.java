package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.Regional_Api.entity.DetallePedidoUpdateDTO;
import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.service.IDetallePedidoService;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "*")
public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detalleService;

    // 1. GET (Por ID)
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerPorId(@PathVariable Integer id) {
        return detalleService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 2. PUT (Actualizar cantidad)
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizarDetalle(
            @PathVariable Integer id,
            @RequestBody DetallePedidoUpdateDTO dto) {
        
        try {
            // Llama al servicio (que ahora es más simple)
            DetallePedido detalleActualizado = detalleService.actualizar(id, dto);
            return ResponseEntity.ok(detalleActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer id) {
        try {
            // Llama al servicio (que ahora es más simple)
            detalleService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}