package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Cliente;
import web.Regional_Api.repository.ClienteRepository;
import web.Regional_Api.service.IClienteService;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository repoCliente;

    @Override
    public List<Cliente> buscarTodos() {
        return repoCliente.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return repoCliente.save(cliente);
    }

    @Override
    public Cliente modificar(Cliente cliente) {
        return repoCliente.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarId(Integer id) {
        return repoCliente.findById(id);
    }

    @Override
    public void eliminar(Integer id) {
        repoCliente.deleteById(id);
    }

    @Override
    public List<Cliente> buscarPorRestaurante(Integer idRestaurante) {
        return repoCliente.findByIdRestaurante(idRestaurante);
    }

    @Override
    public List<Cliente> buscarPorEstado(Integer estado) {
        return repoCliente.findByEstado(estado);
    }
}
