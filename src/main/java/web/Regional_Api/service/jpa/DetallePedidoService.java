package web.Regional_Api.service.jpa;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.DetallePedidoUpdateDTO;
import web.Regional_Api.repository.DetallePedidoRepository;
import web.Regional_Api.service.IDetallePedidoService;

@Service
public class DetallePedidoService implements IDetallePedidoService {

    @Autowired
    private DetallePedidoRepository repoDetalle;

    @Override
    public Optional<DetallePedido> buscarId(Integer id) {
        return repoDetalle.findById(id);
    }

    @Transactional
    public DetallePedido actualizar(Integer idDetalle, DetallePedidoUpdateDTO dto) {
        
        DetallePedido detalle = repoDetalle.findById(idDetalle)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        
        // 1. Actualizar campos (solo cantidad)
        detalle.setCantidad(dto.getCantidad());
        
        // 2. Recalcular el subtotal (fiel al .sql)
        BigDecimal nuevoSubtotal = detalle.getPrecio_unitario()
                                    .multiply(new BigDecimal(dto.getCantidad()));
        detalle.setSubtotal(nuevoSubtotal);
        
        // 3. Guardar el detalle
        repoDetalle.save(detalle);
        
        return detalle;
    }

    @Override
    @Transactional
    public void eliminar(Integer idDetalle) {
        repoDetalle.deleteById(idDetalle);
    }
    
}