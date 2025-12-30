package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.Categoria;
import web.Regional_Api.repository.CategoriaRepository;
import web.Regional_Api.service.ICategoriaService;

@Service
@Transactional(readOnly = true)
public class CategoriaService implements ICategoriaService {
    
    @Autowired
    private CategoriaRepository repoCategoria;
    
    @Override
    public List<Categoria> buscarTodos() {
        return repoCategoria.findAll();
    }
    
    @Override
    @Transactional
    public Categoria guardar(Categoria categoria) {
        return repoCategoria.save(categoria);
    }
    
    @Override
    @Transactional
    public Categoria modificar(Categoria categoria) {
        return repoCategoria.save(categoria);
    }
    
    @Override
    public Optional<Categoria> buscarId(Integer id) {
        return repoCategoria.findById(id);
    }
    @Transactional
    
    @Override
    public void eliminar(Integer id) {
        repoCategoria.deleteById(id);
    }
    
    @Override
    public Optional<Categoria> buscarPorNombre(String nombre) {
        return repoCategoria.findByNombre(nombre);
    }
    
    @Override
    public List<Categoria> buscarPorNombreContiene(String nombre) {
        return repoCategoria.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Override
    public List<Categoria> buscarActivas() {
        return repoCategoria.categoriasActivas();
    }
}
