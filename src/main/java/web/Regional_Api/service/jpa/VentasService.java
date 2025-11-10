package com.comidas.api.service.jpa; 

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import com.comidas.api.entity.Ventas;
import com.comidas.api.repository.VentasRepository; 
import com.comidas.api.service.IVentasService; 
import com.comidas.api.service.ISesionesCajaService; // Necesario para validar sesión

import jakarta.persistence.EntityNotFoundException; 

@Service
@Transactional
public class VentasService implements IVentasService {
    
    @Autowired
    private VentasRepository repoVentas;
    
    @Autowired
    private ISesionesCajaService serviceSesiones;
    
    // ----------------------------------------------------
    // 1. Registrar Venta (Lógica CRÍTICA)
    // ----------------------------------------------------
    @Override
    public Ventas registrarVenta(Ventas venta, Integer idSesion, Integer idCliente) {
        
        // Paso 1: Validar que la Sesión de Caja esté ABIERTA
        serviceSesiones.buscarIdYSucursal(idSesion, /* Necesitamos el idSucursal del pedido */ 1) // Asumimos idSucursal 1 para simplificar
            .filter(s -> s.getEstado() == 1) // 1 = Abierta
            .orElseThrow(() -> new RuntimeException("No se puede registrar la venta. La sesión de caja está cerrada o no existe."));

        // Paso 2: Asignar IDs de contexto
        venta.setIdSesion(idSesion);
        venta.setIdCliente(idCliente);
        venta.setFechaVenta(LocalDateTime.now());
        
        // *En un sistema real, aquí se generaría el número de comprobante único*
        // *Y se actualizaría el estado del Pedido a "Pagado"*

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
        
        // *En un sistema real, se requeriría más lógica: revertir el movimiento de caja, etc.*
    }
}