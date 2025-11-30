package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.dto.AccesoConModuloDTO;
import web.Regional_Api.entity.Acceso;

public interface IAccesoService {
    List<Acceso> getAllAccesos();

    Optional<Acceso> getAccesoById(Integer id);

    Acceso saveAcceso(Acceso acceso);

    void deleteAcceso(Integer id);

    boolean existeAcceso(Integer idModulo, Integer idPerfil);

    void deleteAccesosByPerfil(Integer idPerfil);

    // Métodos para sistema dinámico
    List<Acceso> getAccesosByPerfil(Integer idPerfil);

    boolean tieneAcceso(Integer idPerfil, Integer idModulo);

    List<AccesoConModuloDTO> getAccesosConModuloByPerfil(Integer idPerfil);
}
