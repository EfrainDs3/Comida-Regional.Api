package web.Regional_Api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.Regional_Api.entity.RolUsuario;
import web.Regional_Api.repository.RolUsuarioRepository;

import java.util.List;

@Service
public class RolUsuarioService {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    public List<RolUsuario> getAllRoles() {
        return rolUsuarioRepository.findAll();
    }
}
