package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.Plato;
import web.Regional_Api.repository.PlatoRepository;
import web.Regional_Api.service.IPlatoService;

@Service
@Transactional(readOnly = true)
public class PlatoService implements IPlatoService {

    @Autowired
    private PlatoRepository repoPlato;

    @Override
    public List<Plato> buscarTodos() {
        return repoPlato.findAll();
    }

    @Override
    @Transactional
    public Plato guardar(Plato plato) {
        return repoPlato.save(plato);
    }

    @Override
    @Transactional
    public Plato modificar(Plato plato) {
        return repoPlato.save(plato);
    }

    @Override
    public Optional<Plato> buscarId(Integer id) {
        return repoPlato.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        // Soft delete: cambiar estado a 0
        Optional<Plato> optional = repoPlato.findById(id);
        optional.ifPresent(p -> {
            p.setEstado(0);
            repoPlato.save(p);
        });
    }

    @Override
    public List<Plato> buscarPorCategoria(Integer idCategoria) {
        return repoPlato.buscarPorCategoria(idCategoria);
    }

    @Override
    public List<Plato> buscarDisponiblesPorCategoria(Integer idCategoria) {
        return repoPlato.platosDisponiblesPorCategoria(idCategoria);
    }

    @Override
    public List<Plato> buscarPorNombre(String nombre) {
        return repoPlato.buscarPorNombre(nombre);
    }

    @Override
    public List<Plato> buscar(String search) {
        // Reutilizamos la búsqueda por nombre
        return repoPlato.buscarPorNombre(search);
    }

    @Override
    public List<Plato> buscarPorDisponibilidad(Integer disponible) {
        // Usa el método findByEstado del repository
        return repoPlato.findByEstado(disponible);
    }
}
