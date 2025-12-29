package web.Regional_Api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.PagoSuscripcion;

@Repository
public interface PagoSuscripcionRepository extends JpaRepository<PagoSuscripcion, Long> {
    
    // Obtener todos los pagos de un restaurante
    List<PagoSuscripcion> findByRestauranteIdRestaurante(Integer idRestaurante);
    
    // Obtener pagos por estado
    List<PagoSuscripcion> findByEstado(String estado);
    
    // Obtener pagos de un restaurante con estado específico
    List<PagoSuscripcion> findByRestauranteIdRestauranteAndEstado(Integer idRestaurante, String estado);
    
    // Obtener el último pago de un restaurante
    @Query("SELECT p FROM PagoSuscripcion p WHERE p.restaurante.idRestaurante = :idRestaurante ORDER BY p.fechaPago DESC LIMIT 1")
    Optional<PagoSuscripcion> findUltimoPagoRestaurante(@Param("idRestaurante") Integer idRestaurante);
    
    // Obtener pagos en rango de fechas
    @Query("SELECT p FROM PagoSuscripcion p WHERE p.fechaPago BETWEEN :desde AND :hasta ORDER BY p.fechaPago DESC")
    List<PagoSuscripcion> findPagosPorFecha(
        @Param("desde") LocalDateTime desde,
        @Param("hasta") LocalDateTime hasta
    );
    
    // Obtener pagos pendientes de aprobación
    List<PagoSuscripcion> findByEstadoOrderByFechaPagoAsc(String estado);
    
    // Obtener pagos por método de pago
    List<PagoSuscripcion> findByMetodoPago(String metodoPago);
    
    // Contar pagos pendientes
    long countByEstado(String estado);
    
    // Obtener pagos por restaurante y período
    @Query("SELECT p FROM PagoSuscripcion p WHERE p.restaurante.idRestaurante = :idRestaurante AND p.periodoCubierto = :periodo")
    Optional<PagoSuscripcion> findPagoByRestauranteAndPeriodo(
        @Param("idRestaurante") Integer idRestaurante,
        @Param("periodo") String periodo
    );
}
