package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Acceso;

public interface IAccesoService {
    List<Acceso> getAllAccesos();

    Optional<Acceso> getAccesoById(Integer id);

    Acceso saveAcceso(Acceso acceso);

    void deleteAcceso(Integer id);
}
