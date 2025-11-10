package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;
import web.Regional_Api.entity.PagoSuscripcion;

public interface IPagoSuscripcionService {
    
    List<PagoSuscripcion> buscarTodos();
    
    Optional<PagoSuscripcion> buscarId(Integer id);
    
    PagoSuscripcion guardar(PagoSuscripcion pago);
    
    void eliminar(Integer id);
    
    // Contrato para el método de búsqueda
    List<PagoSuscripcion> buscarPorIdRestaurante(Integer idRestaurante);
}