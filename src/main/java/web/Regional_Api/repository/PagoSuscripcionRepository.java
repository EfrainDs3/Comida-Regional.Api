package web.Regional_Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.PagoSuscripcion;

@Repository
public interface PagoSuscripcionRepository extends JpaRepository<PagoSuscripcion, Integer> {

    @Query("SELECT p FROM PagoSuscripcion p JOIN FETCH p.restaurante")
    List<PagoSuscripcion> findAllWithRestaurante();
    
    @Query("SELECT p FROM PagoSuscripcion p JOIN FETCH p.restaurante WHERE p.restaurante.id_restaurante = :idRestaurante")
    List<PagoSuscripcion> findByRestauranteIdRestaurante(@Param("idRestaurante") Integer idRestaurante);
}
