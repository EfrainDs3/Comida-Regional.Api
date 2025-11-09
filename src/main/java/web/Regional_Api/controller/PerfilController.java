package web.Regional_Api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.Perfil;
import web.Regional_Api.service.jpa.PerfilService;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<Perfil>> getAllPerfiles() {
        return ResponseEntity.ok(perfilService.getAllPerfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> getPerfilById(@PathVariable Integer id) {
        return perfilService.getPerfilById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Perfil> createPerfil(@RequestBody Perfil perfil) {
        Perfil saved = perfilService.savePerfil(perfil);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> updatePerfil(@PathVariable Integer id, @RequestBody Perfil perfil) {
        return perfilService.getPerfilById(id)
                .map(existing -> {
                    perfil.setIdPerfil(id);
                    Perfil updated = perfilService.savePerfil(perfil);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Integer id) {
        if (perfilService.getPerfilById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        perfilService.deletePerfil(id);
        return ResponseEntity.noContent().build();
    }
}
