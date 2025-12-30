package web.Regional_Api.service.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setFechaActualizacion(LocalDateTime.now());
        return repoPedido.save(pedido);
    }

    @Override
    @Transactional
    public Pedido modificar(Pedido pedido) {
        pedido.setFechaActualizacion(LocalDateTime.now());
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
            p.setEstado(0);
            repoPedido.save(p);
        });
    }

    @Override
    public List<Pedido> buscarPorSucursal(Integer idSucursal) {
        return repoPedido.findByIdSucursal(idSucursal);
    }

    @Override
    public List<Pedido> buscarPorEstado(Integer estado) {
        return repoPedido.findByEstado(estado);
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
    public List<Pedido> buscarPorPlato(Integer idPlato) {
        return repoPedido.findByIdPlato(idPlato);
    }

    @Override
    public List<Pedido> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return repoPedido.buscarPorFecha(fechaInicio, fechaFin);
    }

    @Override
    public List<Pedido> buscarPorSucursalYEstado(Integer idSucursal, Integer estado) {
        return repoPedido.buscarPorSucursalYEstado(idSucursal, estado);
    }

    @Override
    public List<Pedido> buscarPorTipoPedido(String tipoPedido) {
        return repoPedido.findByTipoPedido(tipoPedido);
    }

    @Override
    public List<Pedido> buscarPedidosActivos(Integer idSucursal) {
        return repoPedido.buscarPedidosActivosBySucursal(idSucursal);
    }
}
