package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Acceso;
import web.Regional_Api.repository.AccesoRepository;
import web.Regional_Api.service.IAccesoService;

@Service
public class AccesoService implements IAccesoService {

    @Autowired
    private AccesoRepository accesoRepository;

    @Override
    public List<Acceso> getAllAccesos() {
        return accesoRepository.findAll();
    }

    @Override
    public Optional<Acceso> getAccesoById(Integer id) {
        return accesoRepository.findById(id);
    }

    @Override
    public Acceso saveAcceso(Acceso acceso) {
        return accesoRepository.save(acceso);
    }

    @Override
    public void deleteAcceso(Integer id) {
        accesoRepository.deleteById(id);
    }
}
