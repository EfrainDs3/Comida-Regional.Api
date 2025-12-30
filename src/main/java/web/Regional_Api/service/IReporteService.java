package web.Regional_Api.service;

import web.Regional_Api.entity.Reporte;
import web.Regional_Api.entity.SuperAdmin;
import java.util.List;
public interface IReporteService {
    
    // Registrar una nueva acción
    void registrarAccion(SuperAdmin superAdmin, String accion, String detalle, String ip);
    
    // Obtener reportes
    List<Reporte> obtenerTodos();
    List<Reporte> obtenerPorSuperAdmin(Integer id);
}