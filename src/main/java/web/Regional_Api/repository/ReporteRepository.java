package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.Regional_Api.entity.Reporte;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    // Buscar reportes de un superadmin específico
    List<Reporte> findBySuperAdmin_IdSuperAdminOrderByFechaDesc(Integer idSuperAdmin);

    // Buscar reportes por tipo de acción (ej. ver todos los LOGINS)
    List<Reporte> findByAccionOrderByFechaDesc(String accion);

    // Obtener los últimos reportes generales
    List<Reporte> findAllByOrderByFechaDesc();
}