package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.Regional_Api.entity.Reporte;
import web.Regional_Api.entity.ReporteDTO;
import web.Regional_Api.service.IReporteService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restful/superadmin/reportes") // La ruta que busca el frontend
@CrossOrigin(originPatterns = "*", allowCredentials = "true") // Solución al error de CORS con credenciales
public class ReporteController {
    @Autowired
    private IReporteService reporteService;

    // Obtener TODOS los reportes (para la tabla principal)
    @GetMapping
    public ResponseEntity<List<ReporteDTO>> getAllReportes() {
        List<Reporte> reportes = reporteService.obtenerTodos();
        List<ReporteDTO> dtos = reportes.stream().map(ReporteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Obtener reportes de un SuperAdmin específico
    @GetMapping("/superadmin/{id}")
    public ResponseEntity<List<ReporteDTO>> getBySuperAdmin(@PathVariable Integer id) {
        List<Reporte> reportes = reporteService.obtenerPorSuperAdmin(id);
        List<ReporteDTO> dtos = reportes.stream().map(ReporteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}