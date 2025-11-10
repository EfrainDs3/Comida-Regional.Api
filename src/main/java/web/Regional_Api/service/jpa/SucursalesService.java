package com.comidas.api.service.jpa; 

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comidas.api.entity.Sucursales; 
import com.comidas.api.repository.SucursalesRepository; 
import com.comidas.api.service.ISucursalesService; 

import jakarta.persistence.EntityNotFoundException; // Para usar en modificar/eliminar

@Service
public class SucursalesService implements ISucursalesService {
    
    @Autowired
    private SucursalesRepository repoSucursales;
    
    // ----------------------------------------------------
    // 1. READ: Buscar Todos (La firma ya estaba correcta)
    // ----------------------------------------------------
    @Override // <--- AHORA DEBERÍA RECONOCER EL @Override
    public List<Sucursales> buscarTodosPorRestaurante(Integer idRestaurante){
        return repoSucursales.findByIdRestaurante(idRestaurante);
    }
    
    // ----------------------------------------------------
    // 2. CREATE: Guardar (La firma ya estaba correcta)
    // ----------------------------------------------------
    @Override
    public void guardar(Sucursales sucursal, Integer idRestaurante){
        sucursal.setIdRestaurante(idRestaurante); 
        repoSucursales.save(sucursal);
    }
    
    // ----------------------------------------------------
    // 3. UPDATE: Modificar (¡CORRECCIÓN AQUÍ!)
    // ----------------------------------------------------
    @Override
    // NECESITA EL IDRESTAURANTE PARA COINCIDIR CON LA INTERFAZ
    public void modificar(Sucursales sucursalActualizada, Integer idRestaurante){ 
        // Paso 1: Validar que la sucursal exista y pertenezca al restaurante logueado
        // Esto previene que un restaurante modifique la sucursal de otro.
        buscarIdYRestaurante(sucursalActualizada.getIdSucursal(), idRestaurante)
            .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada o acceso denegado para modificar."));
        
        // Paso 2: Ejecutamos el guardado
        repoSucursales.save(sucursalActualizada);
    }
    
    // ----------------------------------------------------
    // 4. READ: Buscar por ID (La firma ya estaba correcta)
    // ----------------------------------------------------
    @Override
    public Optional<Sucursales> buscarIdYRestaurante(Integer idSucursal, Integer idRestaurante){
        return repoSucursales.findByIdSucursalAndIdRestaurante(idSucursal, idRestaurante);
    }
    
    // ----------------------------------------------------
    // 5. DELETE: Eliminar (¡CORRECCIÓN DE FIRMA NECESARIA SI SIGUES LA INTERFAZ ORIGINAL!)
    // ----------------------------------------------------
    @Override 
    // NECESITA EL IDRESTAURANTE PARA COINCIDIR CON LA INTERFAZ
    public void eliminar(Integer idSucursal, Integer idRestaurante){ 
        // Paso 1: Validar que la sucursal exista y pertenezca al restaurante logueado
        buscarIdYRestaurante(idSucursal, idRestaurante)
            .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada o acceso denegado para eliminar."));
        
        // Paso 2: Ejecutar el Soft Delete (usa el ID sin el ID del restaurante)
        repoSucursales.deleteById(idSucursal);
    }
}