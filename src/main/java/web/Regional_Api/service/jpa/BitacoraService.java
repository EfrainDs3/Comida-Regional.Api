package web.Regional_Api.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import web.Regional_Api.entity.BitacoraAccion;
import web.Regional_Api.repository.BitacoraAccionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BitacoraService {


    @Autowired
    private BitacoraAccionRepository bitacoraRepository;

    // Registrar una acción
    public BitacoraAccion registrar(Integer idUsuario, String accion, String tabla, 
                                     Integer idRegistro, String detalles) {
        BitacoraAccion log = new BitacoraAccion(idUsuario, accion, tabla, idRegistro, detalles);
        return bitacoraRepository.save(log);
    }

    // Métodos de conveniencia para acciones comunes
    public void logCreacion(Integer idUsuario, String tabla, Integer idRegistro, String detalles) {
        registrar(idUsuario, "CREAR", tabla, idRegistro, detalles);
    }

    public void logActualizacion(Integer idUsuario, String tabla, Integer idRegistro, String detalles) {
        registrar(idUsuario, "ACTUALIZAR", tabla, idRegistro, detalles);
    }

    public void logEliminacion(Integer idUsuario, String tabla, Integer idRegistro, String detalles) {
        registrar(idUsuario, "ELIMINAR", tabla, idRegistro, detalles);
    }

    public void logLogin(Integer idUsuario, String detalles) {
        registrar(idUsuario, "LOGIN", "usuario", idUsuario, detalles);
    }

    public void logLogout(Integer idUsuario) {
        registrar(idUsuario, "LOGOUT", "usuario", idUsuario, "Cierre de sesión");
    }

    // Consultas
    public List<BitacoraAccion> obtenerTodos() {
        return bitacoraRepository.findTop50ByOrderByFechaHoraDesc();
    }

    public List<BitacoraAccion> obtenerPorUsuario(Integer idUsuario) {
        return bitacoraRepository.findByIdUsuarioOrderByFechaHoraDesc(idUsuario);
    }

    public List<BitacoraAccion> obtenerPorTabla(String tabla) {
        return bitacoraRepository.findByTablaAfectadaOrderByFechaHoraDesc(tabla);
    }

    public List<BitacoraAccion> obtenerPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return bitacoraRepository.findByFechaHoraBetweenOrderByFechaHoraDesc(inicio, fin);
    }

    public List<Object[]> estadisticasPorAccion() {
        return bitacoraRepository.contarPorAccion();
    }
}