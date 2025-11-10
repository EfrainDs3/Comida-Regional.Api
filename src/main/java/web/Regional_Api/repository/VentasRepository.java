package web.Regional_Api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import web.Regional_Api.entity.Ventas;


public interface VentasRepository extends JpaRepository<Ventas, Integer> {
    
    /**
     * Lógica de Caja: Obtener todas las ventas registradas en una sesión específica (para auditoría).
     */
    List<Ventas> findByIdSesion(Integer idSesion);
    
    /**
     * Lógica de Negocio: Obtener ventas por cliente (para historial).
     */
    List<Ventas> findByIdCliente(Integer idCliente);
}
