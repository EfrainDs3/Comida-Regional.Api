package com.comidas.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comidas.api.entity.MovimientosCaja;

public interface MovimientosCajaRepository extends JpaRepository<MovimientosCaja, Integer> {
    
    /**
     * Obtener todos los movimientos asociados a una sesión específica (para detalle).
     */
    List<MovimientosCaja> findByIdSesion(Integer idSesion);
}
