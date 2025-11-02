package web.Regional_Api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
}
