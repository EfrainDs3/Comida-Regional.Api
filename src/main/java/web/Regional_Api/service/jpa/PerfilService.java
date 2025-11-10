package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Perfil;
import web.Regional_Api.repository.PerfilRepository;
import web.Regional_Api.service.IPerfilService;

@Service
public class PerfilService implements IPerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public List<Perfil> getAllPerfiles() {
        return perfilRepository.findAll();
    }

    @Override
    public Optional<Perfil> getPerfilById(Integer id) {
        return perfilRepository.findById(id);
    }

    @Override
    public Perfil savePerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @Override
    public void deletePerfil(Integer id) {
        perfilRepository.deleteById(id);
    }
}
