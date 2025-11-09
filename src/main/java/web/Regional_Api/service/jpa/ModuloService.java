package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Modulo;
import web.Regional_Api.repository.ModuloRepository;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    public List<Modulo> getAllModulos() {
        return moduloRepository.findAll();
    }

    public Optional<Modulo> getModuloById(Integer id) {
        return moduloRepository.findById(id);
    }

    public Modulo saveModulo(Modulo modulo) {
        return moduloRepository.save(modulo);
    }

    public void deleteModulo(Integer id) {
        moduloRepository.deleteById(id);
    }
}
