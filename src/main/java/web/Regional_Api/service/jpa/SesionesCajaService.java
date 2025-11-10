package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.SesionesCaja;
import web.Regional_Api.repository.SesionesCajaRepository;
import web.Regional_Api.service.ISesionesCajaService;


@Service
@Transactional
public class SesionesCajaService implements ISesionesCajaService {
    
    @Autowired
    private SesionesCajaRepository repoSesiones;
    
    // ----------------------------------------------------
    // 1. Apertura de Caja (Lógica CRÍTICA)
    // ----------------------------------------------------
    @Override // <-- ¡Debe estar aquí!
    public void abrirCaja(SesionesCaja nuevaSesion, Integer idSucursal, Integer idUsuarioApertura) {
        // ... (Tu lógica de validación) ...
    }
    
    // ----------------------------------------------------
    // 2. Buscar Todas
    // ----------------------------------------------------
    @Override // <-- ¡Debe estar aquí!
    public List<SesionesCaja> buscarTodasPorSucursal(Integer idSucursal){ // <- Asegúrate de que el tipo de retorno es List<SesionesCaja>
        return repoSesiones.findByIdSucursal(idSucursal);
    }
    
    // ----------------------------------------------------
    // 3. Buscar Sesión Abierta
    // ----------------------------------------------------
    @Override // <-- ¡Debe estar aquí!
    public Optional<SesionesCaja> buscarSesionAbiertaPorSucursal(Integer idSucursal){
        return repoSesiones.findByIdSucursalAndEstado(idSucursal, 1);
    }
    
    // ----------------------------------------------------
    // 4. Buscar por ID y Sucursal (Multi-Sucursal)
    // ----------------------------------------------------
    @Override // <-- ¡Debe estar aquí!
    public Optional<SesionesCaja> buscarIdYSucursal(Integer idSesion, Integer idSucursal){
        return repoSesiones.findByIdSesionAndIdSucursal(idSesion, idSucursal);
    }

    // ----------------------------------------------------
    // 5. Cierre de Caja (Lógica CRÍTICA)
    // ----------------------------------------------------
    @Override // <-- ¡Debe estar aquí!
    public void cerrarCaja(SesionesCaja datosCierre, Integer idSucursal, Integer idUsuarioCierre) {
        // ... (Tu lógica de cierre) ...
    }
    
    // ----------------------------------------------------
    // 6. Eliminar (Soft Delete)
    // ----------------------------------------------------
    @Override // <-- ¡Debe estar aquí!
    public void eliminar(Integer idSesion, Integer idSucursal){ 
        // ... (Tu lógica de eliminación) ...
    }
}