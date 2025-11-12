package web.Regional_Api.service;

import java.util.List; 
import java.util.Optional; 

import web.Regional_Api.entity.SesionesCaja;

public interface ISesionesCajaService {


    Optional<SesionesCaja> buscarId(Integer id); // para que funcione el MovimientosCajaService
    
    // 1. Apertura de Caja (Create - POST)
    // 🌟 SIMPLIFICADO: El objeto ya debe traer todos los IDs necesarios
    void abrirCaja(SesionesCaja nuevaSesion); 

    // 2. Consulta de todas las Sesiones (Read - GET /todos)
    List<SesionesCaja> buscarTodasPorSucursal(Integer idSucursal);
    
    // 🌟 NUEVO: Para ver absolutamente todas las sesiones (prueba rápida)
    List<SesionesCaja> buscarTodas();

    // 3. Consulta de Sesión Abierta Actual (Read - GET)
    Optional<SesionesCaja> buscarSesionAbiertaPorSucursal(Integer idSucursal);

    // 4. Consulta por ID y Sucursal (Read - GET)
    // ⚠️ Se mantiene para la lógica interna del service, pero no se usa en el Controller simplificado.
    Optional<SesionesCaja> buscarIdYSucursal(Integer idSesion, Integer idSucursal);
    
    // 5. Cierre de Caja (Update - PUT)
    // 🌟 SIMPLIFICADO: El objeto ya debe traer todos los IDs necesarios
    void cerrarCaja(SesionesCaja datosCierre); 

    // 6. Eliminación Lógica (Delete)
    // 🌟 SIMPLIFICADO: Solo requiere el ID de la Sesión
    void eliminar(Integer idSesion);
}