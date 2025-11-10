package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;
import web.Regional_Api.entity.Insumos;

public interface IInsumosService {
    
    List<Insumos> buscarTodos();
    
    Optional<Insumos> buscarId(Integer id);

    Insumos guardar(Insumos insumo);

    Insumos modificar(Insumos insumo);

    void eliminar(Integer id);
}
