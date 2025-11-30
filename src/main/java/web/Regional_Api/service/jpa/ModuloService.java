package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Modulo;
import web.Regional_Api.repository.ModuloRepository;
import web.Regional_Api.service.IModuloService;

@Service
public class ModuloService implements IModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    @Override
    public List<Modulo> getAllModulos() {
        return moduloRepository.findAll();
    }

    @Override
    public Optional<Modulo> getModuloById(Integer id) {
        return moduloRepository.findById(id);
    }

    @Override
    public Modulo saveModulo(Modulo modulo) {
        return moduloRepository.save(modulo);
    }

    @Override
    public void deleteModulo(Integer id) {
        // Soft delete: cambiar estado a 0 en lugar de eliminar físicamente
        Optional<Modulo> modulo = moduloRepository.findById(id);
        if (modulo.isPresent()) {
            Modulo m = modulo.get();
            m.setEstado(0);
            moduloRepository.save(m);
        }
    }
}
