package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Usuarios;

public interface IUsuarioService {
    List<Usuarios> getAllUsuarios();

    Usuarios registrarUsuario(Usuarios usuario);

    Usuarios login(String nombreUsuarioLogin, String contraseña);

    Usuarios validarToken(String token);

    Optional<Usuarios> getUsuarioById(Integer id);

    Optional<Usuarios> getUsuarioByLogin(String nombreUsuarioLogin);
}
