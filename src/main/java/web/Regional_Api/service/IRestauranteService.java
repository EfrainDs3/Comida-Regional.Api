package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Restaurante;

public interface IRestauranteService {

    List<Restaurante> buscarTodos();

    Optional<Restaurante> buscarId(Integer id);

    Restaurante guardar(Restaurante restaurante);

    void eliminar(Integer id);

    boolean existsByRuc(String ruc);
}