package web.Regional_Api.service.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.repository.DetallePedidoRepository;
import web.Regional_Api.repository.PedidoRepository;
import web.Regional_Api.service.IDetallePedidoService;

@Service
public class DetallePedidoService implements IDetallePedidoService {

    @Autowired
    private DetallePedidoRepository repoDetallePedido;
    
    @Autowired
    private PedidoRepository repoPedido;

    @Override
    public List<DetallePedido> buscarTodos() {
        return repoDetallePedido.findAll();
    }

    @Override
    @Transactional
    public DetallePedido guardar(DetallePedido detallePedido) {
        detallePedido.calcularSubtotal();
        DetallePedido guardado = repoDetallePedido.save(detallePedido);
        actualizarMontoTotalPedido(detallePedido.getPedido().getIdPedido());
        return guardado;
    }

    @Override
    @Transactional
    public DetallePedido modificar(DetallePedido detallePedido) {
        detallePedido.calcularSubtotal();
        DetallePedido modificado = repoDetallePedido.save(detallePedido);
        actualizarMontoTotalPedido(detallePedido.getPedido().getIdPedido());
        return modificado;
    }

    @Override
    public Optional<DetallePedido> buscarId(Integer id) {
        return repoDetallePedido.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Optional<DetallePedido> optional = repoDetallePedido.findById(id);
        if (optional.isPresent()) {
            DetallePedido detalle = optional.get();
            Integer idPedido = detalle.getPedido().getIdPedido();
            repoDetallePedido.deleteById(id);
            actualizarMontoTotalPedido(idPedido);
        }
    }

    @Override
    public List<DetallePedido> buscarPorPedido(Integer idPedido) {
        return repoDetallePedido.buscarPorPedido(idPedido);
    }

    @Override
    public List<DetallePedido> buscarPorPlato(Integer idPlato) {
        return repoDetallePedido.buscarPorPlato(idPlato);
    }

    @Transactional
    private void actualizarMontoTotalPedido(Integer idPedido) {
        Optional<Pedido> optionalPedido = repoPedido.findById(idPedido);
        if (optionalPedido.isPresent()) {
            Pedido pedido = optionalPedido.get();
            List<DetallePedido> detalles = repoDetallePedido.buscarPorPedido(idPedido);
            
            BigDecimal total = detalles.stream()
                    .map(DetallePedido::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            pedido.setMontoTotal(total);
            pedido.setFechaUpdate(java.time.LocalDateTime.now());
            repoPedido.save(pedido);
        }
    }
}
