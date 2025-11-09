package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Mesas;

public interface IMesasService {
    List<Mesas> buscarTodos();
    Mesas guardar(Mesas mesa);
    Mesas modificar(Mesas mesa);
    Optional<Mesas> buscarId(Integer id);
    void eliminar(Integer id);
}