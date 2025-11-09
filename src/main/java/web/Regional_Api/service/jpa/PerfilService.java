package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Perfil;
import web.Regional_Api.repository.PerfilRepository;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public List<Perfil> getAllPerfiles() {
        return perfilRepository.findAll();
    }

    public Optional<Perfil> getPerfilById(Integer id) {
        return perfilRepository.findById(id);
    }

    public Perfil savePerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    public void deletePerfil(Integer id) {
        perfilRepository.deleteById(id);
    }
}
