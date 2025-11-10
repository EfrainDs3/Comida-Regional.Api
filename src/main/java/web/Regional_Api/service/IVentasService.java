package com.comidas.api.service; 

import java.util.List;
import java.util.Optional;

import com.comidas.api.entity.Ventas;

public interface IVentasService {
    
    // 1. Crear Venta (POST)
    Ventas registrarVenta(Ventas venta, Integer idSesion, Integer idCliente);

    // 2. Buscar todas las ventas por Sesión (GET)
    List<Ventas> buscarTodasPorSesion(Integer idSesion);

    // 3. Buscar venta por ID (GET)
    Optional<Ventas> buscarId(Integer idVenta);
    
    // 4. Anular Venta (DELETE/Soft Delete)
    void anularVenta(Integer idVenta);
}