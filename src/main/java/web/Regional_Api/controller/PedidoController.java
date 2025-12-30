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
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) {
        try {
            Optional<Pedido> pedido = pedidoService.buscarId(id);
            if (pedido.isPresent()) {
                return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        try {
            Pedido pedidoGuardado = pedidoService.guardar(pedido);
            return new ResponseEntity<>(pedidoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> modificar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try {
            pedido.setIdPedido(id);
            Pedido pedidoModificado = pedidoService.modificar(pedido);
            return new ResponseEntity<>(pedidoModificado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            pedidoService.eliminar(id);
            return new ResponseEntity<>("Pedido eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar pedido", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Pedido>> buscarPorSucursal(@PathVariable Integer idSucursal) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorSucursal(idSucursal);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable Integer estado) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorEstado(estado);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pedido>> buscarPorUsuario(@PathVariable Integer idUsuario) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorUsuario(idUsuario);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mesa/{idMesa}")
    public ResponseEntity<List<Pedido>> buscarPorMesa(@PathVariable Integer idMesa) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorMesa(idMesa);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/plato/{idPlato}")
    public ResponseEntity<List<Pedido>> buscarPorPlato(@PathVariable Integer idPlato) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorPlato(idPlato);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tipo/{tipoPedido}")
    public ResponseEntity<List<Pedido>> buscarPorTipoPedido(@PathVariable String tipoPedido) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorTipoPedido(tipoPedido);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sucursal/{idSucursal}/estado/{estado}")
    public ResponseEntity<List<Pedido>> buscarPorSucursalYEstado(
            @PathVariable Integer idSucursal,
            @PathVariable Integer estado) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorSucursalYEstado(idSucursal, estado);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sucursal/{idSucursal}/activos")
    public ResponseEntity<List<Pedido>> buscarPedidosActivos(@PathVariable Integer idSucursal) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPedidosActivos(idSucursal);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
