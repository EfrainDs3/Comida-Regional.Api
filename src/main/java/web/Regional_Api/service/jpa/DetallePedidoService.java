package web.Regional_Api.service.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.repository.DetallePedidoRepository;
import web.Regional_Api.service.IDetallePedidoService;

@Service
public class DetallePedidoService implements IDetallePedidoService {
    
    @Autowired
    private DetallePedidoRepository repoDetallePedido;
    
    @Override
    public List<DetallePedido> buscarTodos() {
        return repoDetallePedido.findAll();
    }
    
    @Override
    public DetallePedido guardar(DetallePedido detallePedido) {
        return repoDetallePedido.save(detallePedido);
    }
    
    @Override
    public DetallePedido modificar(DetallePedido detallePedido) {
        return repoDetallePedido.save(detallePedido);
    }
    
    @Override
    public Optional<DetallePedido> buscarId(Integer id) {
        return repoDetallePedido.findById(id);
    }
    
    @Override
    public void eliminar(Integer id) {
        repoDetallePedido.deleteById(id);
    }
    
    @Override
    public List<DetallePedido> buscarPorPedido(Integer idPedido) {
        return repoDetallePedido.detallesPorPedido(idPedido);
    }
    
    @Override
    public List<DetallePedido> buscarPorPlato(Integer idPlato) {
        return repoDetallePedido.detallesPorPlato(idPlato);
    }
    
    @Override
    public BigDecimal calcularTotalPedido(Integer idPedido) {
        return repoDetallePedido.calcularTotalPedido(idPedido);
    }
}

