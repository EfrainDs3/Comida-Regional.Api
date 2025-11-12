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
        // Lógica simple: solo llama al repositorio
        return repoRestaurante.findAll();
    }

    @Override
    public Optional<Restaurante> buscarId(Integer id) {
        // Lógica simple: solo llama al repositorio
        return repoRestaurante.findById(id);
    }

    @Override
    public Restaurante guardar(Restaurante restaurante) {
        return repoRestaurante.save(restaurante);
    }

    @Override
    public void eliminar(Integer id) {
        repoRestaurante.deleteById(id);
    }
}