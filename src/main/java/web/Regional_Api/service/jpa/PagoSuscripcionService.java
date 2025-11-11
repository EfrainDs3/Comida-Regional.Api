package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.PagoSuscripcion;
import web.Regional_Api.repository.PagoSuscripcionRepository;
import web.Regional_Api.service.IPagoSuscripcionService;

@Service
public class PagoSuscripcionService implements IPagoSuscripcionService {

    @Autowired
    private PagoSuscripcionRepository repoPago;

    @Override
    public List<PagoSuscripcion> buscarTodos() {
        return repoPago.findAllWithRestaurante();
    }

    @Override
    public Optional<PagoSuscripcion> buscarId(Integer id) {
        return repoPago.findById(id);
    }

    @Override
    public PagoSuscripcion guardar(PagoSuscripcion pago) {
        // Lógica simple: solo guarda
        return repoPago.save(pago);
    }

    @Override
    public void eliminar(Integer id) {
        // Lógica simple: borrado físico (esta tabla no tiene 'estado ctmre')
        repoPago.deleteById(id);
    }

    @Override
    public List<PagoSuscripcion> buscarPorIdRestaurante(Integer idRestaurante) {
        return repoPago.findByRestauranteIdRestaurante(idRestaurante);
    }
}