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

import web.Regional_Api.entity.Acceso;
import web.Regional_Api.service.jpa.AccesoService;

@RestController
@RequestMapping("/accesos")
public class AccesoController {

    @Autowired
    private AccesoService accesoService;

    @GetMapping
    public ResponseEntity<List<Acceso>> getAllAccesos() {
        return ResponseEntity.ok(accesoService.getAllAccesos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acceso> getAccesoById(@PathVariable Integer id) {
        return accesoService.getAccesoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createAcceso(@RequestBody Acceso acceso) {
        // Validar si ya existe un acceso con la misma combinación de módulo y perfil
        if (accesoService.existeAcceso(acceso.getIdModulo(), acceso.getIdPerfil())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un acceso para este módulo y perfil");
        }

        Acceso saved = accesoService.saveAcceso(acceso);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acceso> updateAcceso(@PathVariable Integer id, @RequestBody Acceso acceso) {
        return accesoService.getAccesoById(id)
                .map(existing -> {
                    acceso.setIdAcceso(id);
                    Acceso updated = accesoService.saveAcceso(acceso);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcceso(@PathVariable Integer id) {
        if (accesoService.getAccesoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        accesoService.deleteAcceso(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/perfil/{idPerfil}")
    public ResponseEntity<Void> deleteAccesosByPerfil(@PathVariable Integer idPerfil) {
        accesoService.deleteAccesosByPerfil(idPerfil);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para sistema dinámico
    @GetMapping("/perfil/{idPerfil}")
    public ResponseEntity<List<Acceso>> getAccesosByPerfil(@PathVariable Integer idPerfil) {
        List<Acceso> accesos = accesoService.getAccesosByPerfil(idPerfil);
        return ResponseEntity.ok(accesos);
    }

    @GetMapping("/verificar/{idPerfil}/{idModulo}")
    public ResponseEntity<Boolean> verificarAcceso(@PathVariable Integer idPerfil, @PathVariable Integer idModulo) {
        boolean tieneAcceso = accesoService.tieneAcceso(idPerfil, idModulo);
        return ResponseEntity.ok(tieneAcceso);
    }

    // Endpoint mejorado que devuelve accesos con información del módulo
    @GetMapping("/perfil/{idPerfil}/completo")
    public ResponseEntity<List<web.Regional_Api.dto.AccesoConModuloDTO>> getAccesosConModuloByPerfil(
            @PathVariable Integer idPerfil) {
        List<web.Regional_Api.dto.AccesoConModuloDTO> accesos = accesoService.getAccesosConModuloByPerfil(idPerfil);
        return ResponseEntity.ok(accesos);
    }
}
