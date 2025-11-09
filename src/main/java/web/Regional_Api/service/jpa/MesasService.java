package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Mesas;
import web.Regional_Api.repository.MesasRepository;
import web.Regional_Api.service.IMesasService;

@Service
public class MesasService implements IMesasService {

    @Autowired
    private MesasRepository repoMesas; 
    
    @Override
    public List<Mesas> buscarTodos() {
        return repoMesas.findAll();
    }

    @Override
    public Mesas guardar(Mesas mesa){
        return repoMesas.save(mesa);
    }

    @Override 
    public Mesas modificar(Mesas mesa){
        return repoMesas.save(mesa);
    }

    @Override
    public Optional<Mesas> buscarId(Integer id){
        return repoMesas.findById(id);
    }

    @Override
    public void eliminar(Integer id){
        repoMesas.deleteById(id);
    }
}