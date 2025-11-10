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

@Service
@Transactional
public class MovimientosCajaService implements IMovimientosCajaService {

    @Autowired
    private MovimientosCajaRepository repoMovimientos;

    @Autowired
    private ISesionesCajaService serviceSesiones;

    @Override
    public void registrarMovimiento(MovimientosCaja movimiento, Integer idUsuarioRegistro) {
        serviceSesiones
            .buscarIdYSucursal(movimiento.getIdSesion(), 5)
            .filter(s -> s.getEstado() == 1)
            .orElseThrow(() -> new RuntimeException(
                "No se puede registrar el movimiento: La sesión no existe, está cerrada, o no pertenece a tu sucursal."));

        movimiento.setIdUsuario(idUsuarioRegistro);
        repoMovimientos.save(movimiento);
    }

    @Override
    public List<MovimientosCaja> buscarPorSesion(Integer idSesion) {
        return repoMovimientos.findByIdSesion(idSesion);
    }

    @Override
    public BigDecimal calcularTotalMovimientos(Integer idSesion) {
        List<MovimientosCaja> movimientos = buscarPorSesion(idSesion);
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

        movimientos.forEach(m -> {
            if (m.getTipoMovimiento() == MovimientosCaja.TipoMovimiento.Ingreso) {
                total.set(total.get().add(m.getMonto()));
            } else if (m.getTipoMovimiento() == MovimientosCaja.TipoMovimiento.Egreso) {
                total.set(total.get().subtract(m.getMonto()));
            }
        });

        return total.get();
    }
}