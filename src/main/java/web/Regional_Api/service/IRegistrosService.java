package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Registros;

public interface IRegistrosService {

        //CRUD para mis API de Registro
        List<Registros> buscarTodos();

        //Método para listar todos los elementos de Registros
        void guardar(Registros registro);
        //Metodo para guardar registros
        void modificar(Registros regsitro);
        //Metodo modificar registro

        Optional<Registros> buscarId(Integer id);
        //Método de listado de registro

        void eliminar (Integer id);
        //Método para eliminar un registro 

        Optional<Registros> buscarPorUsuarioId(String usuarioId);

        Optional<Registros> buscarPorAccessToken(String accessToken);

        String generarToken(String usuarioId);

        boolean validarToken(String token);


}
