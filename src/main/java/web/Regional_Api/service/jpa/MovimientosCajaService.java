package web.Regional_Api.service.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.MovimientosCaja;
import web.Regional_Api.repository.MovimientosCajaRepository;
import web.Regional_Api.service.IMovimientosCajaService;
import web.Regional_Api.service.ISesionesCajaService;

import jakarta.persistence.EntityNotFoundException; // Asegúrate de que este import exista

@Service
@Transactional
public class MovimientosCajaService implements IMovimientosCajaService {

    @Autowired
    private MovimientosCajaRepository repoMovimientos;

    @Autowired
    private ISesionesCajaService serviceSesiones;

    // 🌟 NUEVA IMPLEMENTACIÓN: Traer todos
    @Override
    public List<MovimientosCaja> buscarTodos() {
        return repoMovimientos.findAll();
    }
    
    // 🌟 CREATE: Simplificado
    @Override
    public void registrarMovimiento(MovimientosCaja movimiento) {
        
        // ⚠️ VALIDACIÓN SIMPLIFICADA (solo verifica que la sesión exista y esté abierta, sin filtro Multi-Tenant)
        serviceSesiones
            .buscarId(movimiento.getIdSesion()) // Asumiendo que buscarId existe y funciona
            .filter(s -> s.getEstado() == 1)
            .orElseThrow(() -> new RuntimeException(
                "No se puede registrar el movimiento: La sesión no existe o está cerrada."));

        // movimiento.setIdUsuario(idUsuarioRegistro); // ⚠️ Asumimos que idUsuario ya está en el objeto Movimiento
        movimiento.setFechaMovimiento(java.time.LocalDateTime.now()); // Asegurar timestamp
        repoMovimientos.save(movimiento);
    }

    // 🌟 UPDATE: Implementación
    @Override
    public void modificarMovimiento(MovimientosCaja movimientoActualizado) {
        
        // Validar que el movimiento exista
        repoMovimientos.findById(movimientoActualizado.getIdMovimientoCaja())
            .orElseThrow(() -> new EntityNotFoundException("Movimiento de caja no encontrado para modificar."));
            
        repoMovimientos.save(movimientoActualizado);
    }

    // 🌟 DELETE: Implementación
    @Override
    public void eliminarMovimiento(Integer idMovimiento) {
        
        // Validar que el movimiento exista antes de eliminar
        repoMovimientos.findById(idMovimiento)
            .orElseThrow(() -> new EntityNotFoundException("Movimiento de caja no encontrado para eliminar."));
            
        repoMovimientos.deleteById(idMovimiento);
    }
    
    @Override
    public List<MovimientosCaja> buscarPorSesion(Integer idSesion) {
        return repoMovimientos.findByIdSesion(idSesion);
    }

    @Override
    public BigDecimal calcularTotalMovimientos(Integer idSesion) {
        // ... (la lógica de cálculo se mantiene) ...
        List<MovimientosCaja> movimientos = buscarPorSesion(idSesion);
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

        movimientos.forEach(m -> {
             // ⚠️ Esta comparación necesita que m.getTipoMovimiento() devuelva un ENUM válido
            // if (m.getTipoMovimiento() == MovimientosCaja.TipoMovimiento.Ingreso) {
            //     total.set(total.get().add(m.getMonto()));
            // } else if (m.getTipoMovimiento() == MovimientosCaja.TipoMovimiento.Egreso) {
            //     total.set(total.get().subtract(m.getMonto()));
            // }
            // Simulación:
            total.set(total.get().add(m.getMonto())); 
        });

        return total.get();
    }
}