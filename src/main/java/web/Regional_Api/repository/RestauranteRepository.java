package web.Regional_Api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {


    Optional<Restaurante> findByRuc(String ruc);

    Optional<Restaurante> findByEmail(String email);

    @Query("SELECT r FROM Restaurante r WHERE LOWER(r.razon_social) LIKE LOWER(CONCAT('%', :razon_social, '%'))")
    List<Restaurante> buscarPorRazonSocial(@Param("razon_social") String razon_social);

    @Query("SELECT r FROM Restaurante r WHERE r.estado_pago = :estado_pago")
    List<Restaurante> buscarPorEstadoPago(@Param("estado_pago") Integer estado_pago);

    @Query("SELECT r FROM Restaurante r WHERE r.razon_social LIKE %:search% OR r.ruc LIKE %:search%")
    List<Restaurante> buscarRestaurantes(@Param("search") String search);
}

<<<<<<< Updated upstream
    


=======
>>>>>>> Stashed changes

