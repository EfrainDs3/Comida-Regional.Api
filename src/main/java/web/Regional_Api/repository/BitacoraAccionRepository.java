package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.Regional_Api.entity.BitacoraAccion;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BitacoraAccionRepository extends JpaRepository<BitacoraAccion, Long> {

    // Buscar por usuario
    List<BitacoraAccion> findByIdUsuarioOrderByFechaHoraDesc(Integer idUsuario);

    // Buscar por tabla afectada
    List<BitacoraAccion> findByTablaAfectadaOrderByFechaHoraDesc(String tablaAfectada);

    // Buscar por rango de fechas
    List<BitacoraAccion> findByFechaHoraBetweenOrderByFechaHoraDesc(LocalDateTime inicio, LocalDateTime fin);

    // Buscar por acción
    List<BitacoraAccion> findByAccionRealizadaContainingIgnoreCaseOrderByFechaHoraDesc(String accion);

    // Últimos N registros
    List<BitacoraAccion> findTop50ByOrderByFechaHoraDesc();

    // Contar acciones por tipo (para estadísticas)
    @Query("SELECT b.accionRealizada, COUNT(b) FROM BitacoraAccion b GROUP BY b.accionRealizada")
    List<Object[]> contarPorAccion();
}