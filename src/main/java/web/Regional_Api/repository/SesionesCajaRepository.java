package com.comidas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comidas.api.entity.SesionesCaja;

public interface SesionesCajaRepository extends JpaRepository<SesionesCaja, Integer> {
    
    // Multi-Sucursal: Buscar todas las sesiones por ID de Sucursal
    List<SesionesCaja> findByIdSucursal(Integer idSucursal);
    
    /**
     * Lógica de Caja: Encuentra la sesión que está actualmente abierta (estado = 1)
     * para una sucursal específica.
     */
    Optional<SesionesCaja> findByIdSucursalAndEstado(Integer idSucursal, Integer estado);
    
    // Multi-Sucursal + GET por ID: Asegura que la sesión pertenezca a la sucursal
    Optional<SesionesCaja> findByIdSesionAndIdSucursal(Integer idSesion, Integer idSucursal);
}
