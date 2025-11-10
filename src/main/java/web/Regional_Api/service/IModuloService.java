package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Modulo;

public interface IModuloService {
    List<Modulo> getAllModulos();

    Optional<Modulo> getModuloById(Integer id);

    Modulo saveModulo(Modulo modulo);

    void deleteModulo(Integer id);
}
