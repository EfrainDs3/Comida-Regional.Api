package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.repository.RestauranteRepository;
import web.Regional_Api.service.IRestauranteService;

@Service
public class RestauranteService implements IRestauranteService {
    
    @Autowired
    private RestauranteRepository repoRestaurante;
    
    @Override
    public List<Restaurante> buscarTodos() {
        return repoRestaurante.findAll();
    }
    
    @Override
    public Restaurante guardar(Restaurante restaurante) {
        return repoRestaurante.save(restaurante);
    }
    
    @Override
    public Restaurante modificar(Restaurante restaurante) {
        return repoRestaurante.save(restaurante);
    }
    
    @Override
    public Optional<Restaurante> buscarId(Integer id) {
        return repoRestaurante.findById(id);
    }
    
    @Override
    public void eliminar(Integer id) {
        repoRestaurante.deleteById(id);
    }
    
    @Override
    public List<Restaurante> buscarPorEstadoPago(Integer estadoPago) {
        return repoRestaurante.findByEstado_pago(estadoPago);
    }
    
    @Override
    public List<Restaurante> buscarPorNombre(String nombre) {
        return repoRestaurante.findByRazon_socialContainingIgnoreCase(nombre);
    }
    
    @Override
    public List<Restaurante> buscar(String search) {
        return repoRestaurante.buscarRestaurantes(search);
    }
    
    @Override
    public Optional<Restaurante> buscarPorRuc(String ruc) {
        return repoRestaurante.findByRuc(ruc);
    }
    
    @Override
    public Optional<Restaurante> buscarPorEmail(String email) {
        return repoRestaurante.findByEmail(email);
    }
}
