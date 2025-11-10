package web.Regional_Api.service.jpa;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.DetallePedidoDTO;
import web.Regional_Api.entity.DetallePedido;
import web.Regional_Api.entity.Pedido;
import web.Regional_Api.repository.DetallePedidoRepository;
import web.Regional_Api.repository.PedidoRepository;
import web.Regional_Api.service.IDetallePedidoService;

@Service
public class DetallePedidoService implements IDetallePedidoService {

    @Autowired
    private DetallePedidoRepository repoDetalle;
    
    @Autowired
    private PedidoRepository repoPedido; // Para actualizar el total del padre

    @Override
    public Optional<DetallePedido> buscarId(Integer id) {
        return repoDetalle.findById(id);
    }

    @Override
    @Transactional // Es una operación compleja, mejor si es transaccional
    public DetallePedido actualizar(Integer idDetalle, DetallePedidoDTO dto) {
        
        // 1. Buscar el detalle
        DetallePedido detalle = repoDetalle.findById(idDetalle)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        
        // 2. Actualizar los campos del detalle
        detalle.setCantidad(dto.getCantidad());
        detalle.setObservaciones(dto.getObservaciones());
        
        // 3. Recalcular el subtotal del detalle
        BigDecimal nuevoSubtotal = detalle.getPrecio_unitario()
                                    .multiply(new BigDecimal(dto.getCantidad()));
        detalle.setSubtotal(nuevoSubtotal);
        
        // 4. Guardar el detalle actualizado
        repoDetalle.save(detalle);
        
        // 5. Recalcular el total del Pedido (Padre)
        Pedido pedidoPadre = detalle.getPedido();
        recalcularTotalPedido(pedidoPadre);
        
        return detalle;
    }

    @Override
    @Transactional
    public void eliminar(Integer idDetalle) {
        // 1. Buscar el detalle
        DetallePedido detalle = repoDetalle.findById(idDetalle)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        
        // 2. Guardar referencia al padre ANTES de borrar
        Pedido pedidoPadre = detalle.getPedido();
        
        // 3. Eliminar el detalle
        repoDetalle.delete(detalle);
        
        // 4. Recalcular el total del Pedido (Padre)
        // (Es importante hacerlo DESPUÉS de borrar el detalle)
        recalcularTotalPedido(pedidoPadre);
    }
    
    private void recalcularTotalPedido(Pedido pedido) {
        
        // Refrescamos la lista de detalles del padre desde la BD
        Pedido pedidoActualizado = repoPedido.findById(pedido.getId_pedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        BigDecimal nuevoTotal = BigDecimal.ZERO;
        
        // Iteramos sobre los detalles restantes
        for (DetallePedido det : pedidoActualizado.getDetalles()) {
            nuevoTotal = nuevoTotal.add(det.getSubtotal());
        }
        
        // Actualizamos el total en el padre y guardamos
        pedidoActualizado.setTotal_pedido(nuevoTotal);
        repoPedido.save(pedidoActualizado);
    }
}