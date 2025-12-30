package web.Regional_Api.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Regional_Api.entity.Reporte;
import web.Regional_Api.entity.SuperAdmin;
import web.Regional_Api.repository.ReporteRepository;
import web.Regional_Api.service.IReporteService;
import java.time.LocalDateTime;
import java.util.List;

@Service // ¡Esta anotación es importante!
public class ReporteService implements IReporteService {
    @Autowired
    private ReporteRepository reporteRepository; // Inyectamos el JPA aquí

    @Override
    @Transactional
    public void registrarAccion(SuperAdmin superAdmin, String accion, String detalle, String ip) {
        try {
            Reporte reporte = new Reporte(accion, detalle, ip, superAdmin);
            reporteRepository.save(reporte);
        } catch (Exception e) {
            System.err.println("Error al guardar reporte: " + e.getMessage());
        }
    }

    @Override
    public List<Reporte> obtenerTodos() {
        return reporteRepository.findAllByOrderByFechaDesc();
    }

    @Override
    public List<Reporte> obtenerPorSuperAdmin(Integer id) {
        return reporteRepository.findBySuperAdmin_IdSuperAdminOrderByFechaDesc(id);
    }
}