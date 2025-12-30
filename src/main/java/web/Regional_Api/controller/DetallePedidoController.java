package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.service.IDetallePedidoService;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedido>> buscarTodos() {
        try {
            List<DetallePedido> detalles = detallePedidoService.buscarTodos();
            return new ResponseEntity<>(detalles, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> buscarPorId(@PathVariable Integer id) {
        try {
            Optional<DetallePedido> detalle = detallePedidoService.buscarId(id);
            if (detalle.isPresent()) {
                return new ResponseEntity<>(detalle.get(), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<DetallePedido> guardar(@RequestBody DetallePedido detallePedido) {
        try {
            DetallePedido detalleGuardado = detallePedidoService.guardar(detallePedido);
            return new ResponseEntity<>(detalleGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> modificar(@PathVariable Integer id, @RequestBody DetallePedido detallePedido) {
        try {
            detallePedido.setIdDetalle(id);
            DetallePedido detalleModificado = detallePedidoService.modificar(detallePedido);
            return new ResponseEntity<>(detalleModificado, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            detallePedidoService.eliminar(id);
            return new ResponseEntity<>("Detalle eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar detalle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<DetallePedido>> buscarPorPedido(@PathVariable Integer idPedido) {
        try {
            List<DetallePedido> detalles = detallePedidoService.buscarPorPedido(idPedido);
            return new ResponseEntity<>(detalles, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/plato/{idPlato}")
    public ResponseEntity<List<DetallePedido>> buscarPorPlato(@PathVariable Integer idPlato) {
        try {
            List<DetallePedido> detalles = detallePedidoService.buscarPorPlato(idPlato);
            return new ResponseEntity<>(detalles, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
