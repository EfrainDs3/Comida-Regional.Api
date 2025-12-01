package web.Regional_Api.controller;

import java.util.HashMap;
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

import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.security.JwtUtil;
import web.Regional_Api.service.IUsuarioService;

@RestController
@RequestMapping("/restful")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private web.Regional_Api.service.IPerfilService perfilService;

    @GetMapping("/usuarios")
    public List<Usuarios> listar() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Usuarios> opt = usuarioService.getUsuarioById(id);
        return opt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> crear(@RequestBody Usuarios usuario) {
        try {
            Usuarios creado = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Usuarios usuario) {
        Optional<Usuarios> actualizado = usuarioService.updateUsuario(id, usuario);
        return actualizado.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok("Usuario eliminado");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/usuarios/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("nombreUsuarioLogin");
        String plainPassword = loginRequest.get("contrasena");

        System.out.println("Intento de login para: " + username);
        System.out.println("Contraseña recibida (texto plano): " + plainPassword);

        Optional<Usuarios> usuarioOpt = usuarioService.getUsuarioByLogin(username);

        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            System.out.println("Usuario encontrado en BD: " + usuario.getNombreUsuarioLogin());

            // Manually hash the plain password to compare with DB
            String hashedPassword = hashPassword(plainPassword);
            System.out.println("Hash calculado: " + hashedPassword);
            System.out.println("Hash en BD: " + usuario.getContrasena());

            if (hashedPassword.equals(usuario.getContrasena())) {
                System.out.println("¡Contraseña correcta!");
                String token = jwtUtil.generarToken(usuario.getNombreUsuarioLogin());

                // Obtener el nombre del perfil real del usuario
                String nombrePerfil = "Sin perfil";
                if (usuario.getRolId() != null) {
                    nombrePerfil = perfilService.getPerfilById(usuario.getRolId())
                            .map(perfil -> perfil.getNombrePerfil())
                            .orElse("Sin perfil");
                }

                // Return token and user info
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("idUsuario", usuario.getIdUsuario());
                response.put("nombreUsuario", usuario.getNombreUsuario());
                response.put("apellidos", usuario.getApellidos());
                response.put("nombreUsuarioLogin", usuario.getNombreUsuarioLogin());
                response.put("idPerfil", usuario.getRolId());
                response.put("nombrePerfil", nombrePerfil);
                response.put("idSucursal", usuario.getIdSucursal()); // Multi-tenant: ID de la sucursal

                return ResponseEntity.ok(response);
            } else {
                System.out.println("¡Contraseña incorrecta!");
            }
        } else {
            System.out.println("Usuario no encontrado en BD");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    // Helper method to hash password using SHA-256
    private String hashPassword(String plainPassword) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            md.update(plainPassword.getBytes());
            byte[] digest = md.digest();
            java.math.BigInteger result = new java.math.BigInteger(1, digest);
            String hash = result.toString(16).toUpperCase();

            while (hash.length() < 64) {
                hash = "0" + hash;
            }

            return hash;
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
}
