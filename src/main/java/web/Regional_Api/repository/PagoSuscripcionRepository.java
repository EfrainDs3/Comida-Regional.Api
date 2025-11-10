package web.Regional_Api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.PagoSuscripcion;

@Repository
public interface PagoSuscripcionRepository extends JpaRepository<PagoSuscripcion, Integer> {
    
    List<PagoSuscripcion> findByRestauranteIdRestaurante(Integer idRestaurante);
}
