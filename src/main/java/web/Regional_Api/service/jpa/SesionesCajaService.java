package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import web.Regional_Api.entity.SesionesCaja;
import web.Regional_Api.repository.SesionesCajaRepository;
import web.Regional_Api.service.ISesionesCajaService;


@Service
@Transactional
public class SesionesCajaService implements ISesionesCajaService {
    
    @Autowired
    private SesionesCajaRepository repoSesiones;
    
    // ----------------------------------------------------
    // 🌟 IMPLEMENTACIÓN AÑADIDA: Buscar por ID (sin filtro)
    // ----------------------------------------------------
    @Override
    public Optional<SesionesCaja> buscarId(Integer id) {
        // Implementación simple para GET por ID (requerido por el controlador)
        return repoSesiones.findById(id); 
    }
    
    // ----------------------------------------------------
    // 🌟 IMPLEMENTACIÓN NUEVA: Buscar Todas
    // ----------------------------------------------------
    @Override
    public List<SesionesCaja> buscarTodas() {
        return repoSesiones.findAll();
    }
    
    // ----------------------------------------------------
    // 1. Apertura de Caja (Lógica CRÍTICA)
    // ----------------------------------------------------
    @Override
    public void abrirCaja(SesionesCaja nuevaSesion) {
        // 🌟 SIMPLIFICACIÓN: La lógica interna debe usar nuevaSesion.getIdSucursal()
        // y nuevaSesion.getIdUsuario() directamente.
        repoSesiones.save(nuevaSesion);
    }
    
    // ----------------------------------------------------
    // 2. Buscar Todas por Sucursal (Se mantiene la lógica original)
    // ----------------------------------------------------
    @Override 
    public List<SesionesCaja> buscarTodasPorSucursal(Integer idSucursal){ 
        return repoSesiones.findByIdSucursal(idSucursal);
    }
    
    // ----------------------------------------------------
    // 3. Buscar Sesión Abierta (Se mantiene la lógica original)
    // ----------------------------------------------------
    @Override
    public Optional<SesionesCaja> buscarSesionAbiertaPorSucursal(Integer idSucursal){
        return repoSesiones.findByIdSucursalAndEstado(idSucursal, 1);
    }
    
    // ----------------------------------------------------
    // 4. Buscar por ID y Sucursal (Se mantiene la lógica original)
    // ----------------------------------------------------
    @Override
    public Optional<SesionesCaja> buscarIdYSucursal(Integer idSesion, Integer idSucursal){
        return repoSesiones.findByIdSesionAndIdSucursal(idSesion, idSucursal);
    }

    // ----------------------------------------------------
    // 5. Cierre de Caja (Lógica CRÍTICA)
    // ----------------------------------------------------
    @Override
    public void cerrarCaja(SesionesCaja datosCierre) {
        // 🌟 SIMPLIFICACIÓN: La lógica interna debe usar datosCierre.getIdSucursal(), etc.
        repoSesiones.save(datosCierre);
    }
    
    // ----------------------------------------------------
    // 6. Eliminar (Soft Delete)
    // ----------------------------------------------------
    @Override
    public void eliminar(Integer idSesion){ 
        // 🌟 SIMPLIFICACIÓN: Ya no se valida por sucursal, solo por existencia.
        repoSesiones.findById(idSesion)
            .orElseThrow(() -> new EntityNotFoundException("Sesión no encontrada para eliminar."));
            
        repoSesiones.deleteById(idSesion);
    }
}