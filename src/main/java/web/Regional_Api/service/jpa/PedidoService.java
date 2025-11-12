package web.Regional_Api.service.jpa;

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
    public Optional<Pedido> buscarId(Integer id) {
        return repoPedido.findById(id);
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        return repoPedido.save(pedido);
    }
}