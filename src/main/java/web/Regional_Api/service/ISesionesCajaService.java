package com.comidas.api.service;

import java.util.List; // <<-- ¡Importante!
import java.util.Optional; // <<-- ¡Importante!

import com.comidas.api.entity.SesionesCaja;

public interface ISesionesCajaService {
    
    // 1. Apertura de Caja (Create - POST)
    void abrirCaja(SesionesCaja nuevaSesion, Integer idSucursal, Integer idUsuarioApertura);

    // 2. Consulta de todas las Sesiones (Read - GET)
    List<SesionesCaja> buscarTodasPorSucursal(Integer idSucursal);

    // 3. Consulta de Sesión Abierta Actual (Read - GET)
    Optional<SesionesCaja> buscarSesionAbiertaPorSucursal(Integer idSucursal);

    // 4. Consulta por ID y Sucursal (Read - GET)
    Optional<SesionesCaja> buscarIdYSucursal(Integer idSesion, Integer idSucursal);
    
    // 5. Cierre de Caja (Update - PUT)
    void cerrarCaja(SesionesCaja datosCierre, Integer idSucursal, Integer idUsuarioCierre);

    // 6. Eliminación Lógica (Delete)
    void eliminar(Integer idSesion, Integer idSucursal);
}