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

import web.Regional_Api.entity.Modulo;
import web.Regional_Api.service.jpa.ModuloService;

@RestController
@RequestMapping("/modulos")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @GetMapping
    public ResponseEntity<List<Modulo>> getAllModulos() {
        return ResponseEntity.ok(moduloService.getAllModulos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modulo> getModuloById(@PathVariable Integer id) {
        return moduloService.getModuloById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Modulo> createModulo(@RequestBody Modulo modulo) {
        Modulo saved = moduloService.saveModulo(modulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modulo> updateModulo(@PathVariable Integer id, @RequestBody Modulo modulo) {
        return moduloService.getModuloById(id)
                .map(existing -> {
                    modulo.setIdModulo(id);
                    Modulo updated = moduloService.saveModulo(modulo);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModulo(@PathVariable Integer id) {
        if (moduloService.getModuloById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        moduloService.deleteModulo(id);
        return ResponseEntity.noContent().build();
    }
}
