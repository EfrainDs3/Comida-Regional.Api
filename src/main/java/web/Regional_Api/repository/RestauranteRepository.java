package web.Regional_Api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
    
    // Buscar restaurante por RUC (único)
    Optional<Restaurante> findByRuc(String ruc);
    
    // Buscar restaurantes activos
    List<Restaurante> findByEstado(Integer estado);
    
    // Buscar restaurantes por nombre (búsqueda parcial)
    List<Restaurante> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar restaurantes con suscripción vencida
    @Query("SELECT r FROM Restaurante r WHERE r.fechaVencimiento < :ahora AND r.estado = 1")
    List<Restaurante> findRestaurantesConSuscripcionVencida(@Param("ahora") LocalDateTime ahora);
    
    // Buscar restaurantes con suscripción próxima a vencer (dentro de X días)
    @Query("SELECT r FROM Restaurante r WHERE r.fechaVencimiento BETWEEN :desde AND :hasta AND r.estado = 1")
    List<Restaurante> findRestaurantesProximoVencimiento(
        @Param("desde") LocalDateTime desde,
        @Param("hasta") LocalDateTime hasta
    );
    
    // Contar restaurantes activos
    long countByEstado(Integer estado);
    
    // Buscar por email de contacto
    Optional<Restaurante> findByEmailContacto(String emailContacto);
}
