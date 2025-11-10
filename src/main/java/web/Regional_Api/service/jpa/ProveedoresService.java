package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Proveedores;
import web.Regional_Api.repository.ProveedoresRepository;
import web.Regional_Api.service.IProveedoresService;

@Service
public class ProveedoresService implements IProveedoresService {
    @Autowired
    private ProveedoresRepository repoProveedores;
    public List<Proveedores> buscarTodos(){
        return repoProveedores.findAll();
    }
    @Override
    public Proveedores guardar(Proveedores proveedores){
        return repoProveedores.save(proveedores);
    }
    @Override
    public Proveedores modificar(Proveedores proveedores){
        return repoProveedores.save(proveedores);
    }
    public Optional<Proveedores> buscarId (Integer id){
        return repoProveedores.findById(id);

    }
    public void eliminar(Integer id){
        repoProveedores.deleteById(id);
    }
    
}
