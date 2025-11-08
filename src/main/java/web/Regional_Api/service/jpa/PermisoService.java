package web.Regional_Api.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.Regional_Api.entity.Permisos;
import web.Regional_Api.repository.PermisoRepository;

import java.util.List;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public List<Permisos> getAllPermisos() {
        return permisoRepository.findAll();
    }
}
