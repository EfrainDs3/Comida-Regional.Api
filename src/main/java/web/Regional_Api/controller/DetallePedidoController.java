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

