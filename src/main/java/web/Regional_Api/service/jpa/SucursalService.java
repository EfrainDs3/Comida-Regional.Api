package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Sucursal;
import web.Regional_Api.repository.SucursalRepository;
import web.Regional_Api.service.ISucursalService;

@Service
public class SucursalService implements ISucursalService {
    
    @Autowired
    private SucursalRepository repoSucursal;
    
    @Override
    public List<Sucursal> buscarTodos() {
        return repoSucursal.findAll();
    }
    
    @Override
    public Sucursal guardar(Sucursal sucursal) {
        return repoSucursal.save(sucursal);
    }
    
    @Override
    public Sucursal modificar(Sucursal sucursal) {
        return repoSucursal.save(sucursal);
    }
    
    @Override
    public Optional<Sucursal> buscarId(Integer id) {
        return repoSucursal.findById(id);
    }
    
    @Override
    public void eliminar(Integer id) {
        repoSucursal.deleteById(id);
    }
    
    @Override
    public List<Sucursal> buscarPorRestaurante(Integer idRestaurante) {
        return repoSucursal.findByIdRestaurante_Id_restaurante(idRestaurante);
    }
    
    @Override
    public List<Sucursal> buscarActivasPorRestaurante(Integer idRestaurante) {
        return repoSucursal.sucursalesActivasPorRestaurante(idRestaurante);
    }
    
    @Override
    public List<Sucursal> buscarPorEstado(String estado) {
        return repoSucursal.findByEstado_sucursal(estado);
    }
    
    @Override
    public Optional<Sucursal> buscarPorNombreYRestaurante(String nombre, Integer idRestaurante) {
        return repoSucursal.findByNombreAndIdRestaurante_Id_restaurante(nombre, idRestaurante);
    }
    
    @Override
    public List<Sucursal> buscarPorCiudad(String ciudad) {
        return repoSucursal.findByCiudad(ciudad);
    }
}

