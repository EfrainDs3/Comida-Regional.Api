package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import web.Regional_Api.service.IDetallePedidoService;

@RestController
@RequestMapping("/api/detalles-pedido")

public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detallePedidoService;

    // ===== CRUD BÁSICO =====

    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> buscarTodos() {
        try {
            List<DetallePedido> detalles = detallePedidoService.buscarTodos();
            List<DetallePedidoDTO> dtos = detalles.stream().map(this::convertirADTO).collect(Collectors.toList());
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            Optional<DetallePedido> detalle = detallePedidoService.buscarId(id);
            if (detalle.isPresent()) {
                DetallePedidoDTO dto = convertirADTO(detalle.get());
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<DetallePedidoDTO> guardar(@RequestBody DetallePedidoDTO dto) {
        try {
            DetallePedido detalle = new DetallePedido();
            if (dto.getIdPedido() != null) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(dto.getIdPedido());
                detalle.setPedido(pedido);
            }
            if (dto.getIdPlato() != null) {
                Plato plato = new Plato();
                plato.setId_plato(dto.getIdPlato());
                detalle.setPlato(plato);
            }
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            DetallePedido detalleGuardado = detallePedidoService.guardar(detalle);
            DetallePedidoDTO dtoGuardado = convertirADTO(detalleGuardado);
            return new ResponseEntity<>(dtoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> modificar(@PathVariable Integer id, @RequestBody DetallePedido detallePedido) {
        try {
            detallePedido.setIdDetalle(id);
            DetallePedido detalleModificado = detallePedidoService.modificar(detallePedido);
            DetallePedidoDTO dto = convertirADTO(detalleModificado);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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

    // ===== BÚSQUEDAS ESPECÍFICAS =====

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<DetallePedidoDTO>> buscarPorPedido(@PathVariable Integer idPedido) {
        try {
            List<DetallePedido> detalles = detallePedidoService.buscarPorPedido(idPedido);
            List<DetallePedidoDTO> dtos = detalles.stream().map(this::convertirADTO).collect(Collectors.toList());
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/plato/{idPlato}")
    public ResponseEntity<List<DetallePedidoDTO>> buscarPorPlato(@PathVariable Integer idPlato) {
        try {
            List<DetallePedido> detalles = detallePedidoService.buscarPorPlato(idPlato);
            List<DetallePedidoDTO> dtos = detalles.stream().map(this::convertirADTO).collect(Collectors.toList());
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== CÁLCULOS =====

    @GetMapping("/{idDetallePedido}/subtotal")
    public ResponseEntity<Double> calcularSubtotal(@PathVariable Integer idDetallePedido) {
        try {
            Double subtotal = detallePedidoService.calcularSubtotal(idDetallePedido);
            return new ResponseEntity<>(subtotal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private DetallePedidoDTO convertirADTO(DetallePedido detalle) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setIdDetalle(detalle.getIdDetalle());
        dto.setIdPedido(detalle.getPedido() != null ? detalle.getPedido().getIdPedido() : null);
        dto.setIdPlato(detalle.getPlato() != null ? detalle.getPlato().getId_plato() : null);
        dto.setNombrePlato(detalle.getPlato() != null ? detalle.getPlato().getNombre() : null);
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
