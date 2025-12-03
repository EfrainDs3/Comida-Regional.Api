package web.Regional_Api.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import web.Regional_Api.entity.Modulo;
import web.Regional_Api.entity.Perfil;
import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.entity.SuperAdmin;

import web.Regional_Api.security.JwtUtil;
import web.Regional_Api.service.ISuperAdminService;
import web.Regional_Api.service.jpa.AccesoService;
import web.Regional_Api.service.jpa.ModuloService;
import web.Regional_Api.service.jpa.PerfilService;
import web.Regional_Api.service.jpa.RestauranteService;
import web.Regional_Api.service.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.UUID;

@RestController
@RequestMapping("/restful/superadmin")
public class SuperAdminController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private ModuloService moduloService;

    @Autowired
    private AccesoService accesoService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ISuperAdminService superAdminService;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ============================================
    // AUTENTICACIÓN SUPERADMIN
    // ============================================

    @PostMapping("/auth/initiate-login")
    public ResponseEntity<?> initiateLogin(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email y contraseña requeridos");
        }

        Optional<SuperAdmin> adminOpt = superAdminService.getSuperAdminByEmail(email);
        if (adminOpt.isEmpty()) {
            // Por seguridad, no indicar si el email existe o no, o usar mensaje genérico
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        SuperAdmin admin = adminOpt.get();

        // Verificar contraseña
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        // Verificar estado
        if (admin.getEstado() == null || admin.getEstado() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cuenta inactiva");
        }

        // Generar token de login (aleatorio)
        String tokenLogin = UUID.randomUUID().toString().substring(0, 6).toUpperCase(); // Token corto de 6 caracteres

        // Guardar token y expiración (10 minutos)
        admin.setTokenLogin(tokenLogin);
        admin.setTokenExpiracion(LocalDateTime.now().plusMinutes(10));
        superAdminService.updateSuperAdmin(admin.getIdSuperAdmin(), admin);

        // Enviar email
        try {
            emailService.enviarTokenSuperAdmin(admin.getEmail(), tokenLogin, admin.getNombres());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar el correo: " + e.getMessage());
        }

        return ResponseEntity.ok(Map.of("message", "Token enviado al correo"));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginSuperAdminAuth(@RequestBody Map<String, String> request) {
        // Reutilizamos la lógica de login existente pero bajo la ruta /auth/login
        return loginSuperAdmin(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginSuperAdmin(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        System.out.println("Intento de login SuperAdmin con token: " + token);

        // Validar token
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token requerido");
        }

        // Buscar super admin por token
        Optional<SuperAdmin> superAdminOpt = superAdminService.getSuperAdminByToken(token);

        if (superAdminOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o no encontrado");
        }

        SuperAdmin superAdmin = superAdminOpt.get();

        // Verificar expiración
        if (superAdmin.getTokenExpiracion() != null && superAdmin.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El token ha expirado");
        }

        // Verificar estado
        if (superAdmin.getEstado() == null || superAdmin.getEstado() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cuenta inactiva o eliminada");
        }

        // Generar JWT
        String jwt = jwtUtil.generarToken(superAdmin.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt); // Retornamos el JWT nuevo, no el token de login
        response.put("idSuperAdmin", superAdmin.getIdSuperAdmin());
        response.put("nombres", superAdmin.getNombres());
        response.put("email", superAdmin.getEmail());
        response.put("rol", superAdmin.getRol());
        response.put("esSuperAdmin", true);

        System.out.println("Login SuperAdmin exitoso: " + superAdmin.getEmail());
        return ResponseEntity.ok(response);
    }

    // ============================================
    // ESTADÍSTICAS GLOBALES
    // ============================================

    @GetMapping("/estadisticas")
    public ResponseEntity<?> getEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRestaurantes", restauranteService.buscarTodos().size());
        // stats.put("totalUsuarios", usuarioService.getAllUsuarios().size()); //
        // REMOVED as per request
        stats.put("totalPerfiles", perfilService.getAllPerfiles().size());
        stats.put("totalModulos", moduloService.getAllModulos().size());

        // Nuevas estadísticas de SuperAdmin
        List<SuperAdmin> admins = superAdminService.getAllSuperAdmins();
        stats.put("totalSuperAdmins", admins.size());
        stats.put("superAdminsActivos", admins.stream().filter(a -> a.getEstado() == 1).count());

        Map<String, Long> porRol = admins.stream()
                .collect(Collectors.groupingBy(SuperAdmin::getRol, Collectors.counting()));
        stats.put("superAdminsPorRol", porRol);

        return ResponseEntity.ok(stats);
    }

    // --- CRUD SuperAdmin ---

    @GetMapping("/super-admins")
    public ResponseEntity<List<SuperAdmin>> getAllSuperAdmins() {
        return ResponseEntity.ok(superAdminService.getAllSuperAdmins());
    }

    @GetMapping("/super-admins/{id}")
    public ResponseEntity<?> getSuperAdminById(@PathVariable Integer id) {
        return superAdminService.getSuperAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/super-admins")
    public ResponseEntity<?> createSuperAdmin(@RequestBody SuperAdmin superAdmin) {
        try {
            SuperAdmin created = superAdminService.createSuperAdmin(superAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/super-admins/{id}")
    public ResponseEntity<?> updateSuperAdmin(@PathVariable Integer id, @RequestBody SuperAdmin superAdmin) {
        try {
            return superAdminService.updateSuperAdmin(id, superAdmin)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/super-admins/{id}")
    public ResponseEntity<?> deleteSuperAdmin(@PathVariable Integer id) {
        try {
            superAdminService.deleteSuperAdmin(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ============================================
    // GESTIÓN DE ROLES (usando tabla perfil)
    // ============================================

    @GetMapping("/roles")
    public ResponseEntity<List<Perfil>> getAllRoles() {
        // Mostrar TODOS los roles para que el SuperAdmin pueda gestionar todo el
        // sistema
        return ResponseEntity.ok(perfilService.getAllPerfiles());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRolById(@PathVariable Integer id) {
        Optional<Perfil> perfilOpt = perfilService.getPerfilById(id);
        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }

        Perfil perfil = perfilOpt.get();
        // Verificar que sea un rol de SuperAdmin
        if (!perfil.getNombrePerfil().startsWith("SUPERADMIN_")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Este no es un rol de SuperAdmin");
        }

        return ResponseEntity.ok(perfil);
    }

    @PostMapping("/roles")
    public ResponseEntity<?> createRol(@RequestBody Perfil perfil) {
        // Asegurar que el nombre empiece con SUPERADMIN_
        if (perfil.getNombrePerfil() == null || perfil.getNombrePerfil().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre del rol es requerido");
        }

        if (!perfil.getNombrePerfil().startsWith("SUPERADMIN_")) {
            perfil.setNombrePerfil("SUPERADMIN_" + perfil.getNombrePerfil());
        }

        perfil.setEstado(1);
        Perfil saved = perfilService.savePerfil(perfil);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> updateRol(@PathVariable Integer id, @RequestBody Perfil perfil) {
        Optional<Perfil> existingOpt = perfilService.getPerfilById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }

        Perfil existing = existingOpt.get();
        // Verificar que sea un rol de SuperAdmin
        if (!existing.getNombrePerfil().startsWith("SUPERADMIN_")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No puedes modificar roles que no son de SuperAdmin");
        }

        // Asegurar que el nuevo nombre también empiece con SUPERADMIN_
        if (!perfil.getNombrePerfil().startsWith("SUPERADMIN_")) {
            perfil.setNombrePerfil("SUPERADMIN_" + perfil.getNombrePerfil());
        }

        perfil.setIdPerfil(id);
        Perfil updated = perfilService.savePerfil(perfil);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable Integer id) {
        Optional<Perfil> perfilOpt = perfilService.getPerfilById(id);
        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }

        Perfil perfil = perfilOpt.get();
        // Verificar que sea un rol de SuperAdmin
        if (!perfil.getNombrePerfil().startsWith("SUPERADMIN_")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No puedes eliminar roles que no son de SuperAdmin");
        }

        perfilService.deletePerfil(id);
        return ResponseEntity.ok("Rol eliminado correctamente");
    }

    // ============================================
    // GESTIÓN DE PERMISOS (usando tabla modulo)
    // ============================================

    @GetMapping("/permisos")
    public ResponseEntity<List<Modulo>> getAllPermisos() {
        return ResponseEntity.ok(moduloService.getAllModulos());
    }

    @GetMapping("/permisos/{id}")
    public ResponseEntity<?> getPermisoById(@PathVariable Integer id) {
        return moduloService.getModuloById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/permisos")
    public ResponseEntity<Modulo> createPermiso(@RequestBody Modulo modulo) {
        modulo.setEstado(1);
        Modulo saved = moduloService.saveModulo(modulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/permisos/{id}")
    public ResponseEntity<?> updatePermiso(@PathVariable Integer id, @RequestBody Modulo modulo) {
        if (moduloService.getModuloById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permiso no encontrado");
        }

        modulo.setIdModulo(id);
        Modulo updated = moduloService.saveModulo(modulo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/permisos/{id}")
    public ResponseEntity<?> deletePermiso(@PathVariable Integer id) {
        if (moduloService.getModuloById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permiso no encontrado");
        }

        moduloService.deleteModulo(id);
        return ResponseEntity.ok("Permiso eliminado correctamente");
    }

    // ============================================
    // ASIGNAR PERMISOS A ROL (usando tabla acceso)
    // ============================================

    @GetMapping("/roles/{idRol}/permisos")
    public ResponseEntity<?> getPermisosByRol(@PathVariable Integer idRol) {
        // Obtener todos los accesos del rol
        List<Acceso> accesos = accesoService.getAllAccesos().stream()
                .filter(a -> a.getIdPerfil().equals(idRol))
                .collect(Collectors.toList());

        // Obtener los IDs de los módulos
        List<Integer> idsModulos = accesos.stream()
                .map(Acceso::getIdModulo)
                .collect(Collectors.toList());

        return ResponseEntity.ok(idsModulos);
    }

    @PostMapping("/roles/{idRol}/permisos")
    public ResponseEntity<?> asignarPermisos(
            @PathVariable Integer idRol,
            @RequestBody List<Integer> idsModulos) {

        System.out.println("Asignando permisos al rol " + idRol + ": " + idsModulos);

        // Verificar que el rol existe
        Optional<Perfil> perfilOpt = perfilService.getPerfilById(idRol);
        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }

        // Eliminar accesos anteriores del rol
        List<Acceso> accesosAnteriores = accesoService.getAllAccesos().stream()
                .filter(a -> a.getIdPerfil().equals(idRol))
                .collect(Collectors.toList());

        for (Acceso acceso : accesosAnteriores) {
            accesoService.deleteAcceso(acceso.getIdAcceso());
        }

        // Crear nuevos accesos
        for (Integer idModulo : idsModulos) {
            Acceso nuevoAcceso = new Acceso();
            nuevoAcceso.setIdPerfil(idRol);
            nuevoAcceso.setIdModulo(idModulo);
            nuevoAcceso.setEstado(1);
            accesoService.saveAcceso(nuevoAcceso);
        }

        return ResponseEntity.ok(Map.of(
                "mensaje", "Permisos asignados correctamente",
                "totalPermisos", idsModulos.size()));
    }

    // ============================================
    // GESTIÓN DE USUARIOS - REMOVED (Use UsuarioController)
    // ============================================

    /*
     * @GetMapping("/usuarios")
     * public ResponseEntity<List<Usuarios>> getAllUsuarios() {
     * return ResponseEntity.ok(usuarioService.getAllUsuarios());
     * }
     * 
     * @PostMapping("/usuarios")
     * public ResponseEntity<?> createUsuario(@RequestBody Usuarios usuario) {
     * // ...
     * return ResponseEntity.status(HttpStatus.CREATED).body(saved);
     * }
     * 
     * @PutMapping("/usuarios/{id}")
     * public ResponseEntity<?> updateUsuario(@PathVariable Integer id, @RequestBody
     * Usuarios usuario) {
     * // ...
     * return ResponseEntity.ok(updated);
     * }
     * 
     * @DeleteMapping("/usuarios/{id}")
     * public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
     * usuarioService.deleteUsuario(id);
     * return ResponseEntity.ok("Usuario eliminado");
     * }
     */

    // ============================================
    // GESTIÓN DE RESTAURANTES
    // ============================================

    @GetMapping("/restaurantes")
    public ResponseEntity<List<Restaurante>> getAllRestaurantes() {
        return ResponseEntity.ok(restauranteService.buscarTodos());
    }

    @GetMapping("/restaurantes/{id}")
    public ResponseEntity<?> getRestauranteById(@PathVariable Integer id) {
        return restauranteService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @Autowired
    private web.Regional_Api.service.jpa.SucursalesService sucursalesService;

    @PostMapping("/restaurantes")
    public ResponseEntity<Restaurante> createRestaurante(@RequestBody Restaurante restaurante) {
        restaurante.setEstado(1);
        Restaurante saved = restauranteService.guardar(restaurante);

        // Crear Sucursal Principal Automática
        web.Regional_Api.entity.Sucursales sucursalPrincipal = new web.Regional_Api.entity.Sucursales();
        sucursalPrincipal.setIdRestaurante(saved.getId_restaurante());
        sucursalPrincipal.setNombre("Sucursal Principal");
        sucursalPrincipal.setDireccion(saved.getDireccion_principal());
        sucursalPrincipal.setEstado(1);
        sucursalesService.guardar(sucursalPrincipal);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/restaurantes/{id}")
    public ResponseEntity<?> updateRestaurante(@PathVariable Integer id, @RequestBody Restaurante restaurante) {
        if (restauranteService.buscarId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante no encontrado");
        }

        restaurante.setId_restaurante(id);
        Restaurante updated = restauranteService.guardar(restaurante);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/restaurantes/{id}")
    public ResponseEntity<?> deleteRestaurante(@PathVariable Integer id) {
        if (restauranteService.buscarId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante no encontrado");
        }

        restauranteService.eliminar(id);
        return ResponseEntity.ok("Restaurante eliminado correctamente");
    }
}
