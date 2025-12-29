package web.Regional_Api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import web.Regional_Api.entity.Mesas;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.entity.PedidoDTO;
import web.Regional_Api.entity.Sucursales;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.service.IPedidoService;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    // ===== CRUD BÁSICO =====

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
    public ResponseEntity<Pedido> guardar(@RequestBody PedidoDTO dto) {
        try {
            Pedido pedido = new Pedido();
            // Set relationships
            if (dto.getIdUsuario() != null) {
                Usuarios usuario = new Usuarios();
                usuario.setIdUsuario(dto.getIdUsuario());
                pedido.setUsuario(usuario);
            }
            if (dto.getIdSucursal() != null) {
                Sucursales sucursal = new Sucursales();
                sucursal.setIdSucursal(dto.getIdSucursal());
                pedido.setSucursal(sucursal);
            }
            if (dto.getIdMesa() != null) {
                Mesas mesa = new Mesas();
                mesa.setId_mesa(dto.getIdMesa());
                pedido.setMesa(mesa);
            }
            pedido.setEstado(dto.getEstado());
            pedido.setTipoPedido(dto.getTipoPedido());
            pedido.setNombreCliente(dto.getNombreCliente());
            pedido.setTelefonoCliente(dto.getTelefonoCliente());
            Pedido pedidoGuardado = pedidoService.guardar(pedido);
            return new ResponseEntity<>(pedidoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Agregar logging para debug
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> modificar(@PathVariable Integer id, @RequestBody PedidoDTO dto) {
        try {
            Optional<Pedido> optional = pedidoService.buscarId(id);
            if (!optional.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            Pedido pedido = optional.get();
            // Update relationships
            if (dto.getIdUsuario() != null) {
                Usuarios usuario = new Usuarios();
                usuario.setIdUsuario(dto.getIdUsuario());
                pedido.setUsuario(usuario);
            }
            if (dto.getIdSucursal() != null) {
                Sucursales sucursal = new Sucursales();
                sucursal.setIdSucursal(dto.getIdSucursal());
                pedido.setSucursal(sucursal);
            }
            if (dto.getIdMesa() != null) {
                Mesas mesa = new Mesas();
                mesa.setId_mesa(dto.getIdMesa());
                pedido.setMesa(mesa);
            }
            pedido.setEstado(dto.getEstado());
            pedido.setTipoPedido(dto.getTipoPedido());
            pedido.setNombreCliente(dto.getNombreCliente());
            pedido.setTelefonoCliente(dto.getTelefonoCliente());
            // Update fechaUpdate
            pedido.setFechaUpdate(LocalDateTime.now());
            Pedido pedidoModificado = pedidoService.modificar(pedido);
            return new ResponseEntity<>(pedidoModificado, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // Agregar logging para debug
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

    // ===== BÚSQUEDAS ESPECÍFICAS =====

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
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estado) {
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
            @PathVariable String estado) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorSucursalYEstado(idSucursal, estado);
            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== CÁLCULOS =====

    @GetMapping("/{idPedido}/total")
    public ResponseEntity<Double> calcularTotal(@PathVariable Integer idPedido) {
        try {
            Double total = pedidoService.calcularTotal(idPedido);
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
