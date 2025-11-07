package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.Regional_Api.entity.RolUsuario;
import web.Regional_Api.service.RolUsuarioService;

import java.util.List;

@RestController
public class RolUsuarioController {

    @Autowired
    private RolUsuarioService rolUsuarioService;

    @GetMapping("/roles")
    public List<RolUsuario> getAllRoles() {
        return rolUsuarioService.getAllRoles();
    }
}
