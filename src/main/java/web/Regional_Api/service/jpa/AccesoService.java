package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Acceso;
import web.Regional_Api.repository.AccesoRepository;

@Service
public class AccesoService {

    @Autowired
    private AccesoRepository accesoRepository;

    public List<Acceso> getAllAccesos() {
        return accesoRepository.findAll();
    }

    public Optional<Acceso> getAccesoById(Integer id) {
        return accesoRepository.findById(id);
    }

    public Acceso saveAcceso(Acceso acceso) {
        return accesoRepository.save(acceso);
    }

    public void deleteAcceso(Integer id) {
        accesoRepository.deleteById(id);
    }
}
