package web.Regional_Api.service.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // MUY IMPORTANTE

import web.Regional_Api.entity.Insumos;
import web.Regional_Api.entity.MovimientosInventario;
import web.Regional_Api.entity.MovimientosInventario.TipoMovimiento; // Enum anidado
import web.Regional_Api.repository.InsumosRepository; 
import web.Regional_Api.repository.MovimientosInventarioRepository;
import web.Regional_Api.service.IMovimientosInventarioService;

@Service
public class MovimientosInventarioService implements IMovimientosInventarioService {

    @Autowired
    private MovimientosInventarioRepository repoMovimientos;
    
    @Autowired
    private InsumosRepository repoInsumos; // Para actualizar el stock

    @Override
    @Transactional(readOnly = true) // Solo lectura
    public List<MovimientosInventario> buscarTodos() {
        return repoMovimientos.findAll();
    }

    @Override
    @Transactional(readOnly = true) // Solo lectura
    public Optional<MovimientosInventario> buscarId(Integer id) {
        return repoMovimientos.findById(id);
    }

    @Override
    @Transactional // Transacción de LECTURA/ESCRITURA
    public MovimientosInventario guardar(MovimientosInventario movimiento) {
        
        // 1. Validar y obtener el insumo
        Insumos insumo = repoInsumos.findById(movimiento.getInsumo().getId_insumo())
                .orElseThrow(() -> new RuntimeException("El insumo no existe"));

        BigDecimal cantidad = movimiento.getCantidad();
        TipoMovimiento tipo = movimiento.getTipo_movimiento();

        // 2. Lógica para actualizar el stock_actual del insumo
        if (tipo == TipoMovimiento.Salida) {
            insumo.setStock_actual(insumo.getStock_actual().subtract(cantidad));
            if (insumo.getStock_actual().compareTo(BigDecimal.ZERO) < 0) {
                 throw new RuntimeException("Stock insuficiente");
            }
        } else {
            // "Entrada" y "Ajuste" suman al stock
            insumo.setStock_actual(insumo.getStock_actual().add(cantidad));
        }

        // 3. Guardar el insumo actualizado
        repoInsumos.save(insumo);

        // 4. Guardar el nuevo movimiento
        return repoMovimientos.save(movimiento);
    }
}
