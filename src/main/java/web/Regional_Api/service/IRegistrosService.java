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

<<<<<<< HEAD
        Optional<Registros> buscarPorAccessToken(String accessToken);

        String generarToken(String clienteId);

        boolean validarToken(String token);
=======
        String generarToken(String usuarioId);
>>>>>>> f3962c3143b401d61ac21cb62ba9db512927d280


}
