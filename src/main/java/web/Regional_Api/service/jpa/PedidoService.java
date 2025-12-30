package web.Regional_Api.service.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.repository.PedidoRepository;
import web.Regional_Api.service.IPedidoService;

@Service
public class PedidoService implements IPedidoService {

    @Autowired
    private PedidoRepository repoPedido;

    @Override
    public List<Pedido> buscarTodos() {
        return repoPedido.findAll();
    }

    @Override
    @Transactional
    public Pedido guardar(Pedido pedido) {
        if (pedido.getFechaHora() == null) {
            pedido.setFechaHora(LocalDateTime.now());
        }
        if (pedido.getFechaUpdate() == null) {
            pedido.setFechaUpdate(LocalDateTime.now());
        }
        if (pedido.getEstadoPedido() == null) {
            pedido.setEstadoPedido("Pendiente");
        }
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(d -> d.setPedido(pedido));
            BigDecimal total = pedido.getDetalles().stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            pedido.setMontoTotal(total);
        } else {
            pedido.setMontoTotal(BigDecimal.ZERO);
        }
        return repoPedido.save(pedido);
    }

    @Override
    @Transactional
    public Pedido modificar(Pedido pedido) {
        pedido.setFechaUpdate(LocalDateTime.now());
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(d -> d.setPedido(pedido));
            BigDecimal total = pedido.getDetalles().stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            pedido.setMontoTotal(total);
        }
        return repoPedido.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarId(Integer id) {
        return repoPedido.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Optional<Pedido> optional = repoPedido.findById(id);
        optional.ifPresent(p -> {
            p.setEstadoPedido("Cancelado");
            repoPedido.save(p);
        });
    }

    @Override
    public List<Pedido> buscarPorSucursal(Integer idSucursal) {
        return repoPedido.findByIdSucursal(idSucursal);
    }

    @Override
    public List<Pedido> buscarPorEstado(String estadoPedido) {
        return repoPedido.findByEstadoPedido(estadoPedido);
    }

    @Override
    public List<Pedido> buscarPorUsuario(Integer idUsuario) {
        return repoPedido.findByIdUsuario(idUsuario);
    }

    @Override
    public List<Pedido> buscarPorMesa(Integer idMesa) {
        return repoPedido.findByIdMesa(idMesa);
    }

    @Override
    public List<Pedido> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return repoPedido.buscarPorFecha(fechaInicio, fechaFin);
    }

    @Override
    public List<Pedido> buscarPorSucursalYEstado(Integer idSucursal, String estadoPedido) {
        return repoPedido.buscarPorSucursalYEstado(idSucursal, estadoPedido);
    }

    @Override
    public List<Pedido> buscarPorTipoPedido(String tipoPedido) {
        return repoPedido.findByTipoPedido(tipoPedido);
    }

    @Override
    public List<Pedido> buscarPedidosActivos(Integer idSucursal) {
        return repoPedido.buscarPedidosActivosBySucursal(idSucursal);
    }

    @Override
    public List<Pedido> buscarPorNombreCliente(String nombreCliente) {
        return repoPedido.buscarPorNombreCliente(nombreCliente);
    }

    @Override
    public Optional<Pedido> buscarUltimoPedidoActivoByMesa(Integer idMesa) {
        return repoPedido.findUltimoPedidoActivoByMesa(idMesa);
    }

    @Override
    public List<Pedido> buscarPorPlato(Integer idPlato) {
        return repoPedido.buscarPorPlato(idPlato);
    }
}
