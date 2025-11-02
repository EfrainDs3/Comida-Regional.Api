package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.service.UsuarioService;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuarios> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }
}
