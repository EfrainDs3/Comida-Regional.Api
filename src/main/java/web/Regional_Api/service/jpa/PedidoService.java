package web.Regional_Api.service.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Pedido guardar(Pedido pedido) {
        return repoPedido.save(pedido);
    }
    
    @Override
    public Pedido modificar(Pedido pedido) {
        return repoPedido.save(pedido);
    }
    
    @Override
    public Optional<Pedido> buscarId(Integer id) {
        return repoPedido.findById(id);
    }
    
    @Override
    public void eliminar(Integer id) {
        repoPedido.deleteById(id);
    }
    
    @Override
    public List<Pedido> buscarPorSucursal(Integer idSucursal) {
        return repoPedido.findByIdSucursal_Id_sucursal(idSucursal);
    }
    
    @Override
    public List<Pedido> buscarPorEstado(String estado) {
        return repoPedido.findByEstado_pedido(estado);
    }
    
    @Override
    public Optional<Pedido> buscarPorNumeroPedido(String numeroPedido) {
        return repoPedido.findByNumero_pedido(numeroPedido);
    }
    
    @Override
    public List<Pedido> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return repoPedido.pedidosPorRangoFechas(fechaInicio, fechaFin);
    }
    
    @Override
    public List<Pedido> buscarPorSucursalYEstado(Integer idSucursal, String estado) {
        return repoPedido.pedidosPorSucursalYEstado(idSucursal, estado);
    }
    
    @Override
    public List<Pedido> buscarPorSucursalYFechas(Integer idSucursal, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return repoPedido.pedidosPorSucursalYFechas(idSucursal, fechaInicio, fechaFin);
    }
}
