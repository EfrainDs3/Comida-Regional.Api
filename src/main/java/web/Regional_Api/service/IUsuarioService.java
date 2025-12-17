package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Usuarios;

public interface IUsuarioService {
    List<Usuarios> getAllUsuarios();

    Usuarios registrarUsuario(Usuarios usuario);

    Usuarios login(String nombreUsuarioLogin, String contrasena);

    Usuarios validarToken(String token);

    Optional<Usuarios> getUsuarioById(Integer id);

    Optional<Usuarios> getUsuarioByLogin(String nombreUsuarioLogin);

    Optional<Usuarios> updateUsuario(Integer id, Usuarios usuario);

    void deleteUsuario(Integer id);

    List<Usuarios> getUsuariosBySucursal(Integer idSucursal);
}
