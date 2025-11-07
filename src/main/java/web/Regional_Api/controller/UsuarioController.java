package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuarios> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    public ResponseEntity<List<Usuarios>> getAllUsuariosEntity() {
        List<Usuarios> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrarusuario(@RequestBody Usuarios usuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuarios usuarioRegistrado = usuarioService.registrarUsuario(usuario);

            response.put("success", true);
            response.put("message", "Usuario registrado exitosamente");
            response.put("usuario", Map.of(
                "idUsuario", usuarioRegistrado.getIdUsuario(),
                "nombreUsuario", usuarioRegistrado.getNombreUsuario(),
                "email", usuarioRegistrado.getEmail(),
                "accessToken", usuarioRegistrado.getAccessToken()
            ));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
            
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = credentials.get("email");
            String contraseña = credentials.get("contraseña");
            
            if (email == null || contraseña == null) {
                response.put("success", false);
                response.put("message", "Email y contraseña son requeridos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Realizar login
            Usuarios usuario = usuarioService.login(email, contraseña);
            
            response.put("success", true);
            response.put("message", "Login exitoso");
            response.put("usuario", Map.of(
                "idUsuario", usuario.getIdUsuario(),
                "nombreUsuario", usuario.getNombreUsuario(),
                "email", usuario.getEmail(),
                "rolId", usuario.getRolId(),
                "accessToken", usuario.getAccessToken()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/validar-token")
    public ResponseEntity<Map<String, Object>> validarToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) Map<String, String> body) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            String token = null;
            
            // Intentar obtener el token del header Authorization
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
            // Si no está en el header, intentar obtenerlo del body
            else if (body != null && body.containsKey("token")) {
                token = body.get("token");
            }
            
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token no proporcionado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Validar el token
            Usuarios usuario = usuarioService.validarToken(token);
            
            response.put("success", true);
            response.put("message", "Token válido");
            response.put("usuario", Map.of(
                "idUsuario", usuario.getIdUsuario(),
                "nombreUsuario", usuario.getNombreUsuario(),
                "email", usuario.getEmail(),
                "rolId", usuario.getRolId()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
