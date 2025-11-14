package web.Regional_Api.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.Registros;
import web.Regional_Api.security.JwtUtil;
import web.Regional_Api.service.IRegistrosService;
import web.Regional_Api.service.jpa.UsuarioService;

@RestController
@RequestMapping("/restful")
public class RegistrosController {

    @Autowired
    private IRegistrosService serviceRegistro;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registros")
    public List<Registros> buscarTodos() {
        return serviceRegistro.buscarTodos();
    }

    @PostMapping("/registros")
    public Registros guardar(@RequestBody Registros registro) {
        // Si `id_usuario` viene como número, verificar existencia y asociar
        String incoming = registro.getId_usuario();
        if (incoming != null && incoming.matches("\\d+")) {
            try {
                Integer usuarioId = Integer.valueOf(incoming);
                var usuarioOpt = usuarioService.getUsuarioById(usuarioId);
                if (usuarioOpt.isPresent()) {
                    registro.setId_usuario(String.valueOf(usuarioOpt.get().getIdUsuario()));
                } else {
                    registro.setId_usuario(null);
                }
            } catch (NumberFormatException ex) {
                registro.setId_usuario(null);
            }
        } else {
            registro.setId_usuario(null);
        }

        String claveOriginal = registro.getEmail() + registro.getNombres() + registro.getApellidos();
        registro.setLlave_secreta(claveOriginal);
        serviceRegistro.guardar(registro);

        return registro;
    }

    @PutMapping("/registros")
    public Registros modificar(@RequestBody Registros registro) {
        serviceRegistro.modificar(registro);
        return registro;
    }

    @GetMapping("/registros/{id}")
    public Optional<Registros> buscarId(@PathVariable("id") Integer id) {
        return serviceRegistro.buscarId(id);
    }

    @DeleteMapping("/registros/{id}")
    public String eliminar(@PathVariable Integer id) {
        serviceRegistro.eliminar(id);
        return "Registro eliminado";
    }

    @PostMapping("/token")
    public ResponseEntity<?> obtenerToken(@RequestBody Map<String, String> credenciales) {
        // Aceptar únicamente `id_usuario`
        String idUsuario = credenciales.get("id_usuario");

        if (idUsuario == null || idUsuario.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se requiere 'id_usuario'");
        }
        String llaveSecreta = credenciales.get("llave_secreta");

        Optional<Registros> user = serviceRegistro.buscarPorIdUsuario(idUsuario);
        if (user.isPresent() && passwordEncoder.matches(llaveSecreta, user.get().getLlave_secreta())) {
            String token = serviceRegistro.generarToken(idUsuario);
            Registros registro = user.get();
            registro.setAccess_token(token);
            serviceRegistro.guardar(registro);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}
