package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import web.Regional_Api.entity.Ventas;
import web.Regional_Api.repository.VentasRepository;
import web.Regional_Api.service.IVentasService;
import web.Regional_Api.service.ISesionesCajaService;

@Service
@Transactional
public class VentasService implements IVentasService {
    
    @Autowired
    private VentasRepository repoVentas;
    
    @Autowired
    private ISesionesCajaService serviceSesiones;
    
    // ----------------------------------------------------
    // 🌟 IMPLEMENTACIÓN NUEVA: Buscar Todas
    // ----------------------------------------------------
    @Override
    public List<Ventas> buscarTodas() {
        return repoVentas.findAll();
    }
    
    // ----------------------------------------------------
    // 1. Registrar Venta (Lógica CRÍTICA - SIMPLIFICADA)
    // ----------------------------------------------------
    @Override
    public Ventas registrarVenta(Ventas venta) {
        
        // 🌟 SIMPLIFICACIÓN: Se usa el idSesion que viene en el objeto 'venta'
        // Paso 1: Validar que la Sesión de Caja esté ABIERTA
        // ⚠️ Nota: Esta validación Multi-Tenant está incompleta/simplificada, asumiendo que 
        // buscarId() existe y que getEstado() es 1 (Abierta).
        serviceSesiones.buscarId(venta.getIdSesion()) 
             .filter(s -> s.getEstado() == 1) 
             .orElseThrow(() -> new RuntimeException("No se puede registrar la venta. La sesión de caja está cerrada o no existe."));

        // Paso 2: Asignar datos de contexto (solo la fecha/hora)
        // ⚠️ Se asume que venta.getIdSesion() y venta.getIdCliente() ya fueron establecidos por Jackson.
        venta.setFechaVenta(LocalDateTime.now());
        
        return repoVentas.save(venta);
    }
    
    // ----------------------------------------------------
    // 2. Buscar todas por Sesión (GET)
    // ----------------------------------------------------
    @Override
    public List<Ventas> buscarTodasPorSesion(Integer idSesion) {
        return repoVentas.findByIdSesion(idSesion);
    }

    // ----------------------------------------------------
    // 3. Buscar por ID (GET)
    // ----------------------------------------------------
    @Override
    public Optional<Ventas> buscarId(Integer idVenta) {
        return repoVentas.findById(idVenta);
    }
    
    // ----------------------------------------------------
    // 4. Anular Venta (Soft Delete)
    // ----------------------------------------------------
    @Override
    public void anularVenta(Integer idVenta) {
        Ventas venta = repoVentas.findById(idVenta)
            .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada."));
            
        // Aquí se usaría el método deleteById() de JPA para activar el Soft Delete
        repoVentas.deleteById(venta.getIdVenta());
    }

    @Override
public Ventas modificarVenta(Ventas ventaActualizada) {
    
    // 1. Validar que la venta exista (esencial para una modificación)
    repoVentas.findById(ventaActualizada.getIdVenta())
        .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada para modificar."));
        
    // 2. Guardar la actualización. Spring Data JPA detecta el ID existente y ejecuta un UPDATE.
    return repoVentas.save(ventaActualizada);
}
}