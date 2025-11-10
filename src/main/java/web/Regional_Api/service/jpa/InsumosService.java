package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Insumos;
import web.Regional_Api.repository.InsumosRepository;
import web.Regional_Api.service.IInsumosService;

@Service
public class InsumosService implements IInsumosService {

    @Autowired
    private InsumosRepository repoInsumos;

    @Override
    public List<Insumos> buscarTodos() {
        return repoInsumos.findAll();
    }

    @Override
    public Optional<Insumos> buscarId(Integer id) {
        return repoInsumos.findById(id);
    }

    @Override
    public Insumos guardar(Insumos insumo) {
        // Asignar estado 1 por defecto al guardar
        insumo.setEstado(1); 
        return repoInsumos.save(insumo);
    }

    @Override
    public Insumos modificar(Insumos insumo) {
        return repoInsumos.save(insumo);
    }

    @Override
    public void eliminar(Integer id) {
        // El @SQLDelete en la entidad se encargará del borrado lógico
        repoInsumos.deleteById(id); 
    }
}