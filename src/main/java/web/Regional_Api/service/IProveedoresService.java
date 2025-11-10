package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Proveedores;

public interface IProveedoresService {
    List<Proveedores> buscarTodos();
    //Metodo para listar todos los metodos de Proveedores

    Proveedores guardar(Proveedores proveedores);
    //Metodo para guardar Proveedores

    Proveedores modificar(Proveedores proveedores);
    //Metodo para modificar Proveedores

    Optional<Proveedores> buscarId(Integer id);
    //Metodo para listar un curso

    void eliminar(Integer id);
    //Método para eliminar un curso

    
}