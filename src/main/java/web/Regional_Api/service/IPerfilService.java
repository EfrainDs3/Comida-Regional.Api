package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Perfil;

public interface IPerfilService {
    List<Perfil> getAllPerfiles();

    Optional<Perfil> getPerfilById(Integer id);

    Perfil savePerfil(Perfil perfil);

    void deletePerfil(Integer id);
}
