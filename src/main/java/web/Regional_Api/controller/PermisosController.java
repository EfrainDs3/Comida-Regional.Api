package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.Regional_Api.entity.Permisos;
import web.Regional_Api.service.jpa.PermisoService;

import java.util.List;

@RestController
public class PermisosController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping("/permisos")
    public List<Permisos> getAllPermisos() {
        return permisoService.getAllPermisos();
    }
}
