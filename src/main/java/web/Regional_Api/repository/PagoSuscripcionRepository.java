package web.Regional_Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.PagoSuscripcion;

@Repository
public interface PagoSuscripcionRepository extends JpaRepository<PagoSuscripcion, Integer> {
    
    // RF10: Buscar historial de pagos por ID de restaurante
    // Spring Data JPA lo crea automáticamente:
    // "Buscar por (Restaurante -> IdRestaurante)"
    List<PagoSuscripcion> findByRestauranteIdRestaurante(Integer idRestaurante);

    // (Opcional) Buscar pagos por estado
    List<PagoSuscripcion> findByEstadoPago(Integer estado);
}
