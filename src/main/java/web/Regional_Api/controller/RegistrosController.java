package web.Regional_Api.controller;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.Regional_Api.entity.Registros;
import web.Regional_Api.security.JwtUtil;
import web.Regional_Api.service.IRegistrosService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/restful")
public class RegistrosController {
    @Autowired
    private IRegistrosService serviceRegistros;

    @Autowired
    private JwtUtil jwtUtil;

<<<<<<< HEAD
        @Autowired
        private BCryptPasswordEncoder passwordEncoder;
        @Autowired
        private web.Regional_Api.service.jpa.UsuarioService usuarioService;
=======
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
>>>>>>> f3962c3143b401d61ac21cb62ba9db512927d280

    @GetMapping("/registros")
    public List<Registros> buscartodos() {
        return serviceRegistros.buscarTodos();
    }
    @PostMapping("/registros")
    public Registros guardar(@RequestBody Registros registro) {
        registro.setUsuario_id(null);
        String claveOriginal = registro.getEmail() + 
                    registro.getNombres() + registro.getApellidos();
        registro.setLlave_secreta(claveOriginal);
        serviceRegistros.guardar(registro);
        return registro;
    }
    @PutMapping("/registros")
    public Registros modificar(@RequestBody Registros registro) {
        serviceRegistros.modificar(registro);
        return registro;
    }
    @GetMapping("/registros/{id}")
    public Optional<Registros> buscarId(@PathVariable("id") Integer id) {
        return serviceRegistros.buscarId(id);
    }
    @DeleteMapping("/registros/{id}")
    public String eliminar(@PathVariable Integer id){
        serviceRegistros.eliminar(id);
        return "Registro eliminado";
    }

<<<<<<< HEAD
        @PostMapping("/registros")
        public Registros guardar(@RequestBody Registros registro) {
            // Si el cliente_id/usuario_id viene como número, se interpreta como id de Usuarios
            String incomingCliente = registro.getUsuario_id();
            if (incomingCliente != null && incomingCliente.matches("\\d+")) {
                try {
                    Integer usuarioId = Integer.valueOf(incomingCliente);
                    var usuarioOpt = usuarioService.getUsuarioById(usuarioId);
                    if (usuarioOpt.isPresent()) {
                        // Asociar el registro al id_usuario (guardar como cadena)
                        registro.setUsuario_id(String.valueOf(usuarioOpt.get().getIdUsuario()));
                    } else {
                        // Si no existe el usuario, generar el cliente_id tradicional
                        registro.setUsuario_id(null);
                    }
                } catch (NumberFormatException ex) {
                    registro.setUsuario_id(null);
                }
            } else {
                // comportamiento por defecto: calcular cliente_id desde nombres/apellidos/email
                registro.setUsuario_id(null);
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
        public String eliminar (@PathVariable Integer id){
            serviceRegistro.eliminar(id);
            return "Registro eliminado";
        }
        
        @PostMapping("/token")
        public ResponseEntity<?> obtenerToken(@RequestBody Map<String, String> credenciales) {
        String clienteId = credenciales.get("cliente_id");
        String llaveSecreta = credenciales.get("llave_secreta");
        Optional<Registros> user = serviceRegistro.buscarTodos().stream()
                    .filter(r -> r.getUsuario_id().equals(clienteId))
                    .findFirst();
        if (user.isPresent() && passwordEncoder.matches(llaveSecreta, user.get().getLlave_secreta())){
            String token = jwtUtil.generateDeveloperToken(clienteId);
=======
    @PostMapping("/token")
    public ResponseEntity<?> obtenerToken(@RequestBody 
                            Map<String,String> credenciales) {
        String usuarioId = credenciales.get("usuario_id");
        String llaveSecreta = credenciales.get("llave_secreta");
>>>>>>> f3962c3143b401d61ac21cb62ba9db512927d280

        Optional<Registros> user = serviceRegistros
            .buscarTodos()
            .stream()
            .filter(r -> r.getUsuario_id().equals(usuarioId))
            .findFirst();
        if(user.isPresent() && passwordEncoder.matches(llaveSecreta,
                            user.get().getLlave_secreta())){
            String token = serviceRegistros.generarToken(usuarioId);

            Registros registro = user.get();
            registro.setAccess_token(token); // guardar en Registros.java
            serviceRegistros.guardar(registro); // guarda en la BD

            return ResponseEntity.ok(Collections
                .singletonMap("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Credenciales incorrectas");
    }     
}
