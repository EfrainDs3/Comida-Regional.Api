package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Restaurante;

public interface IRestauranteService {
    // CRUD para el API de Restaurantes
    
    List<Restaurante> buscarTodos();
    // Método para listar todos los restaurantes activos
    
    Restaurante guardar(Restaurante restaurante);
    // Método para guardar un nuevo restaurante
    
    Restaurante modificar(Restaurante restaurante);
    // Método para modificar un restaurante
    
    Optional<Restaurante> buscarId(Integer id);
    // Método para listar un restaurante por ID
    
    void eliminar(Integer id);
    // Método para eliminar (soft delete) un restaurante
    
    List<Restaurante> buscarPorEstadoPago(Integer estadoPago);
    // Método para buscar por estado de pago
    
    List<Restaurante> buscarPorNombre(String nombre);
    // Método para buscar por nombre o razón social
    
    List<Restaurante> buscar(String search);
    // Método para búsqueda general por nombre o RUC
    
    Optional<Restaurante> buscarPorRuc(String ruc);
    // Método para buscar por RUC
    
    Optional<Restaurante> buscarPorEmail(String email);
    // Método para buscar por Email
}
