package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import web.Regional_Api.entity.Sucursales;
import web.Regional_Api.repository.SucursalesRepository;
import web.Regional_Api.service.ISucursalesService;

@Service
public class SucursalesService implements ISucursalesService {

    @Autowired
    private SucursalesRepository repoSucursales;
    
    // -----------------------------------------------------------
    // 🌟 IMPLEMENTACIÓN AÑADIDA: Buscar por ID (sin filtro)
    // -----------------------------------------------------------
    @Override
    public Optional<Sucursales> buscarId(Integer id) {
        // Usa el método estándar de JPA para buscar por la clave primaria
        return repoSucursales.findById(id); 
    }
    
    // -----------------------------------------------------------
    // IMPLEMENTACIONES EXISTENTES
    // -----------------------------------------------------------
    @Override
    public List<Sucursales> buscarTodos() {
        return repoSucursales.findAll();
    }
    
    @Override
    public List<Sucursales> buscarTodosPorRestaurante(Integer idRestaurante) {
        return repoSucursales.findByIdRestaurante(idRestaurante);
    }

   
 @Override
    public Sucursales guardar(Sucursales sucursal) {
        // Al hacer return del save, devolvemos el objeto YA con el ID generado por la BD
        return repoSucursales.save(sucursal); 
    }

    @Override
    public void modificar(Sucursales sucursalActualizada) {
        // Validación de existencia simple (no Multi-Tenant)
        repoSucursales.findById(sucursalActualizada.getIdSucursal())
             .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada para modificar."));
             
        repoSucursales.save(sucursalActualizada);
    }

    @Override
    public Optional<Sucursales> buscarIdYRestaurante(Integer idSucursal, Integer idRestaurante) {
        return repoSucursales.findByIdSucursalAndIdRestaurante(idSucursal, idRestaurante);
    }

    @Override
    public void eliminar(Integer idSucursal) {
        // Validación de existencia simple (no Multi-Tenant)
        repoSucursales.findById(idSucursal)
             .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada para eliminar."));

        repoSucursales.deleteById(idSucursal);
    }
}