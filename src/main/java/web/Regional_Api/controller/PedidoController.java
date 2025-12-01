package web.Regional_Api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping; // <-- El DTO actualizado
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Importa todas tus entidades
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.DetallePedidoDTO;
import web.Regional_Api.entity.Mesas;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.entity.PedidoDTO;
import web.Regional_Api.entity.Plato;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.repository.MesasRepository;
import web.Regional_Api.repository.PlatoRepository;
import web.Regional_Api.repository.UsuarioRepository;
import web.Regional_Api.service.IPedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    @Autowired
    private MesasRepository mesaRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private PlatoRepository platoRepo;

    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Integer id) {
        return pedidoService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO dto) {

        Optional<Mesas> mes = mesaRepo.findById(dto.getId_mesa());
        Optional<Usuarios> usu = usuarioRepo.findById(dto.getId_usuario()); // Fiel al nuevo nombre

        if (mes.isEmpty() || usu.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Mesa o Usuario no encontrado.");
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mes.get());
        pedido.setUsuario(usu.get());
        pedido.setNotas(dto.getNotas());

        pedido.setEstado_pedido("En preparación");
        List<DetallePedido> detallesEntidad = new ArrayList<>();

        for (DetallePedidoDTO detDto : dto.getDetalles()) {
            Optional<Plato> pla = platoRepo.findById(detDto.getId_plato());
            if (pla.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Plato con ID " + detDto.getId_plato() + " no encontrado.");
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPlato(pla.get());
            detalle.setCantidad(detDto.getCantidad());

            detalle.setPrecio_unitario(detDto.getPrecio_unitario());
            BigDecimal subtotal = detDto.getPrecio_unitario()
                    .multiply(new BigDecimal(detDto.getCantidad()));
            detalle.setSubtotal(subtotal);

            detalle.setPedido(pedido);
            detallesEntidad.add(detalle);
        }

        pedido.setDetalles(detallesEntidad);
        Pedido nuevoPedido = pedidoService.guardar(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Integer id,
            @RequestBody String nuevoEstado) {

        Optional<Pedido> opt = pedidoService.buscarId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pedido pedido = opt.get();
        pedido.setEstado_pedido(nuevoEstado);

        Pedido actualizado = pedidoService.guardar(pedido);
        return ResponseEntity.ok(actualizado);
    }
}