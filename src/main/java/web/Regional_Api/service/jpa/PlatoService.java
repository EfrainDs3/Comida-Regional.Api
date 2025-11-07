package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Plato;
import web.Regional_Api.repository.PlatoRepository;
import web.Regional_Api.service.IPlatoService;

@Service
public class PlatoService implements IPlatoService {
    
    @Autowired
    private PlatoRepository repoPlato;
    
    @Override
    public List<Plato> buscarTodos() {
        return repoPlato.findAll();
    }
    
    @Override
    public Plato guardar(Plato plato) {
        return repoPlato.save(plato);
    }
    
    @Override
    public Plato modificar(Plato plato) {
        return repoPlato.save(plato);
    }
    
    @Override
    public Optional<Plato> buscarId(Integer id) {
        return repoPlato.findById(id);
    }
    
    @Override
    public void eliminar(Integer id) {
        repoPlato.deleteById(id);
    }
    
    @Override
    public List<Plato> buscarPorCategoria(Integer idCategoria) {
        return repoPlato.findByIdCategoria_Id_categoria(idCategoria);
    }
    
    @Override
    public List<Plato> buscarDisponiblesPorCategoria(Integer idCategoria) {
        return repoPlato.platosDisponiblesPorCategoria(idCategoria);
    }
    
    @Override
    public List<Plato> buscarPorSucursal(Integer idSucursal) {
        return repoPlato.findByIdSucursal_Id_sucursal(idSucursal);
    }
    
    @Override
    public List<Plato> buscarDisponiblesPorSucursal(Integer idSucursal) {
        return repoPlato.platosDisponiblesPorSucursal(idSucursal);
    }
    
    @Override
    public List<Plato> buscarPorNombre(String nombre) {
        return repoPlato.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Override
    public List<Plato> buscar(String search) {
        return repoPlato.buscarPlatos(search);
    }
    
    @Override
    public List<Plato> buscarPorDisponibilidad(Integer disponible) {
        return repoPlato.findByDisponible(disponible);
    }
}

