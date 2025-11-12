package web.Regional_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.service.AuthorizationService;
import web.Regional_Api.service.jpa.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    public List<Usuarios> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getUsuarioById(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuarios usuario) {
        try {
            Usuarios created = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Integer id, @RequestBody Usuarios usuario) {
        try {
            return usuarioService.updateUsuario(id, usuario)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
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
                "nombre_usuario_login", usuarioRegistrado.getNombreUsuarioLogin(),
                "dni", usuarioRegistrado.getDniUsuario(),
                "idSucursal", usuarioRegistrado.getIdSucursal(),
                "fechaCreacion", usuarioRegistrado.getFechaCreacion(),
                "ultimoLogin", usuarioRegistrado.getUltimoLogin(),
                "accessToken", usuarioRegistrado.getAccessToken()
            ));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
            
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Aceptamos tanto 'email' (compatibilidad) como 'nombre_usuario_login' como clave de usuario
            String nombreLogin = credentials.get("nombre_usuario_login");
            if (nombreLogin == null) {
                nombreLogin = credentials.get("email");
            }
            String contrasena = credentials.get("contrasena");

            if (nombreLogin == null || contrasena == null) {
                response.put("success", false);
                response.put("message", "Nombre de usuario (nombre_usuario_login) y contrasena son requeridos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Realizar login (por nombre de usuario de login)
            Usuarios usuario = usuarioService.login(nombreLogin, contrasena);
            
            response.put("success", true);
            response.put("message", "Login exitoso");
            response.put("usuario", Map.of(
                "idUsuario", usuario.getIdUsuario(),
                "nombreUsuario", usuario.getNombreUsuario(),
                "nombre_usuario_login", usuario.getNombreUsuarioLogin(),
                "rolId", usuario.getRolId(),
                "idSucursal", usuario.getIdSucursal(),
                "fechaCreacion", usuario.getFechaCreacion(),
                "ultimoLogin", usuario.getUltimoLogin(),
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
            
         
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
            
            else if (body != null && body.containsKey("token")) {
                token = body.get("token");
            }
            
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token no proporcionado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
           
            Usuarios usuario = usuarioService.validarToken(token);
            
            response.put("success", true);
            response.put("message", "Token válido");
            response.put("usuario", Map.of(
                "idUsuario", usuario.getIdUsuario(),
                "nombreUsuario", usuario.getNombreUsuario(),
                "nombre_usuario_login", usuario.getNombreUsuarioLogin(),
                "rolId", usuario.getRolId(),
                "idSucursal", usuario.getIdSucursal(),
                "fechaCreacion", usuario.getFechaCreacion(),
                "ultimoLogin", usuario.getUltimoLogin()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/verificar-acceso")
    public ResponseEntity<Map<String, Object>> verificarAcceso(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) Map<String, String> body) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (body == null || !body.containsKey("nombreModulo")) {
                response.put("success", false);
                response.put("message", "El nombre del módulo es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            String token = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else if (body.containsKey("token")) {
                token = body.get("token");
            }

            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token no proporcionado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Usuarios usuario = usuarioService.validarToken(token);
            boolean hasAccess = authorizationService.userHasAccess(usuario.getNombreUsuarioLogin(), body.get("nombreModulo"));

            response.put("success", true);
            response.put("hasAccess", hasAccess);
            response.put("message", hasAccess ? "Acceso concedido" : "Acceso denegado");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
