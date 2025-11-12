package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.Ventas;

public interface IVentasService {
    
    // 1. Crear Venta (POST)
    // 🌟 SIMPLIFICADO: El objeto 'venta' ahora contiene idSesion e idCliente.
    Ventas registrarVenta(Ventas venta); 

    // 🌟 NUEVO: 5. Modificar Venta (PUT)
    Ventas modificarVenta(Ventas ventaActualizada); // <-- ¡AÑADIR ESTO!

    // 🌟 NUEVO: Buscar todas las ventas sin filtro
    List<Ventas> buscarTodas();
    
    // 2. Buscar todas las ventas por Sesión (GET)
    List<Ventas> buscarTodasPorSesion(Integer idSesion);

    // 3. Buscar venta por ID (GET)
    Optional<Ventas> buscarId(Integer idVenta);
    
    // 4. Anular Venta (DELETE/Soft Delete)
    // Se mantiene con un parámetro, ya que solo necesita el ID
    void anularVenta(Integer idVenta);
}