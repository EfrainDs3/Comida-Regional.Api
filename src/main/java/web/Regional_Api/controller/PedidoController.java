package web.Regional_Api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import web.Regional_Api.service.IPedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> buscarTodos() {
        try {
            List<Pedido> pedidos = pedidoService.buscarTodos();
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) {
        try {
            Optional<Pedido> pedido = pedidoService.buscarId(id);
            if (pedido.isPresent()) {
                return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        try {
            Pedido pedidoGuardado = pedidoService.guardar(pedido);
            return new ResponseEntity<>(pedidoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> modificar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try {
            pedido.setIdPedido(id);
            Pedido pedidoModificado = pedidoService.modificar(pedido);
            return new ResponseEntity<>(pedidoModificado, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            pedidoService.eliminar(id);
            return new ResponseEntity<>("Pedido cancelado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al cancelar pedido", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Pedido>> buscarPorSucursal(@PathVariable Integer idSucursal) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorSucursal(idSucursal);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/estado/{estadoPedido}")
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estadoPedido) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorEstado(estadoPedido);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pedido>> buscarPorUsuario(@PathVariable Integer idUsuario) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorUsuario(idUsuario);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/mesa/{idMesa}")
    public ResponseEntity<List<Pedido>> buscarPorMesa(@PathVariable Integer idMesa) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorMesa(idMesa);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/tipo/{tipoPedido}")
    public ResponseEntity<List<Pedido>> buscarPorTipoPedido(@PathVariable String tipoPedido) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorTipoPedido(tipoPedido);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Pedido>> buscarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorFecha(fechaInicio, fechaFin);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/sucursal/{idSucursal}/estado/{estadoPedido}")
    public ResponseEntity<List<Pedido>> buscarPorSucursalYEstado(
            @PathVariable Integer idSucursal,
            @PathVariable String estadoPedido) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorSucursalYEstado(idSucursal, estadoPedido);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/sucursal/{idSucursal}/activos")
    public ResponseEntity<List<Pedido>> buscarPedidosActivos(@PathVariable Integer idSucursal) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPedidosActivos(idSucursal);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cliente/{nombreCliente}")
    public ResponseEntity<List<Pedido>> buscarPorNombreCliente(@PathVariable String nombreCliente) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorNombreCliente(nombreCliente);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/mesa/{idMesa}/ultimo-activo")
    public ResponseEntity<Pedido> buscarUltimoPedidoActivoByMesa(@PathVariable Integer idMesa) {
        try {
            Optional<Pedido> pedido = pedidoService.buscarUltimoPedidoActivoByMesa(idMesa);
            if (pedido.isPresent()) {
                return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
