package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Cliente;

public interface IClienteService {
    List<Cliente> buscarTodos();
    Cliente guardar(Cliente cliente);
    Cliente modificar(Cliente cliente);
    Optional<Cliente> buscarId(Integer id);
    void eliminar(Integer id);
    List<Cliente> buscarPorRestaurante(Integer idRestaurante);
    List<Cliente> buscarPorEstado(Integer estado);
}
