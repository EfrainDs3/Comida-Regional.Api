package web.Regional_Api.service.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.repository.DetallePedidoRepository;
import web.Regional_Api.repository.PedidoRepository;
import web.Regional_Api.service.IPedidoService;

@Service
public class PedidoService implements IPedidoService {

    @Autowired
    private PedidoRepository repoPedido;
    
    @Autowired
    private DetallePedidoRepository repoDetallePedido;

    @Override
    public List<Pedido> buscarTodos() {
        return repoPedido.findAll();
    }

    @Override
    @Transactional
    public Pedido guardar(Pedido pedido) {
        return repoPedido.save(pedido);
    }

    @Override
    @Transactional
    public Pedido modificar(Pedido pedido) {
        return repoPedido.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarId(Integer id) {
        return repoPedido.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        // Soft delete: cambiar estado a "cancelado"
        Optional<Pedido> optional = repoPedido.findById(id);
        optional.ifPresent(p -> {
            p.setEstado("cancelado");
            repoPedido.save(p);
        });
    }

    @Override
    public List<Pedido> buscarPorSucursal(Integer idSucursal) {
        return repoPedido.buscarPorSucursal(idSucursal);
    }

    @Override
    public List<Pedido> buscarPorEstado(String estado) {
        return repoPedido.buscarPorEstado(estado);
    }

    @Override
    public List<Pedido> buscarPorUsuario(Integer idUsuario) {
        return repoPedido.buscarPorUsuario(idUsuario);
    }

    @Override
    public List<Pedido> buscarPorMesa(Integer idMesa) {
        return repoPedido.buscarPorMesa(idMesa);
    }

    @Override
    public List<Pedido> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return repoPedido.buscarPorFecha(fechaInicio, fechaFin);
    }

    @Override
    public List<Pedido> buscarPorSucursalYEstado(Integer idSucursal, String estado) {
        return repoPedido.buscarPorSucursalYEstado(idSucursal, estado);
    }

    @Override
    public Double calcularTotal(Integer idPedido) {
        List<DetallePedido> detalles = repoDetallePedido.buscarPorPedido(idPedido);
        return detalles.stream()
                .mapToDouble(d -> d.getSubtotal().doubleValue())
                .sum();
    }
}
