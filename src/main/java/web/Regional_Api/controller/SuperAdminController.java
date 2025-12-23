package web.Regional_Api.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import web.Regional_Api.entity.Usuarios;

import web.Regional_Api.security.SuperAdminJwtUtil;
import web.Regional_Api.service.ISuperAdminService;
import web.Regional_Api.service.jpa.AccesoService;
import web.Regional_Api.service.jpa.ModuloService;
import web.Regional_Api.service.jpa.PerfilService;
import web.Regional_Api.service.jpa.RestauranteService;
import web.Regional_Api.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/restful/superadmin")
public class SuperAdminController {

    @Autowired
    private SuperAdminJwtUtil jwtUtil;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private web.Regional_Api.service.jpa.UsuarioService usuarioService;

    // ============================================
    // AUTENTICACIÓN SUPERADMIN
    // ============================================

    @PostMapping("/auth/initiate-login")
    public ResponseEntity<?> initiateLogin(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email y contraseña requeridos");
        }

        Optional<SuperAdmin> adminOpt = superAdminService.getSuperAdminByEmail(email);
        if (adminOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        SuperAdmin admin = adminOpt.get();
        String hashedPassword = hashPassword(password);

        if (!hashedPassword.equals(admin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        if (admin.getEstado() == null || admin.getEstado() != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("SuperAdmin inactivo");
        }

        String token = jwtUtil.generateToken(admin.getEmail());

        // GUARDAR TOKEN EN BD
        admin.setTokenLogin(token);

        String oldPassword = admin.getPassword();
        admin.setPassword(null);

        superAdminService.updateSuperAdmin(admin.getIdSuperAdmin(), admin);

        admin.setPassword(oldPassword);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        // Enviar el token por correo electrónico (NO bloquear si falla)
        try {
            emailService.enviarTokenSuperAdmin(email, token, admin.getNombres());
            System.out.println("📧 Token enviado al correo: " + email);
        } catch (Exception emailEx) {
            System.err.println("⚠️ Error al enviar email (login continúa): " + emailEx.getMessage());
            // El login sigue funcionando aunque el email falle
        }

        return ResponseEntity.ok(response);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash de contraseña", e);
        }
    }

    // SHA-256 en formato HEXADECIMAL (compatible con Usuarios y login)
    private String hashPasswordHex(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainPassword.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            java.math.BigInteger result = new java.math.BigInteger(1, digest);
            String hash = result.toString(16).toUpperCase();
            while (hash.length() < 64) {
                hash = "0" + hash;
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    @PostMapping("/auth/login")
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

        // Verificar estado
        if (superAdmin.getEstado() == null || superAdmin.getEstado() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cuenta inactiva o eliminada");
        }

        // Generar JWT
        String jwt = jwtUtil.generarToken(superAdmin.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);

        response.put("idUsuario", superAdmin.getIdSuperAdmin());
        response.put("nombres", superAdmin.getNombres());
        response.put("email", superAdmin.getEmail());
        response.put("rol", superAdmin.getRol());

        response.put("nombrePerfil", "Super Administrador");
        response.put("idSucursal", 0);

        Map<String, Object> user = new HashMap<>();
        user.put("id_superadmin", superAdmin.getIdSuperAdmin());
        user.put("nombres", superAdmin.getNombres());
        user.put("email", superAdmin.getEmail());
        user.put("rol", superAdmin.getRol());
        response.put("user", user);

        System.out.println("Login SuperAdmin exitoso: " + superAdmin.getEmail());

        // bitacoraService.logLogin(superAdmin.getIdSuperAdmin(),
        // "SuperAdmin login: " + superAdmin.getEmail());

        return ResponseEntity.ok(response);
    }

    // ============================================
    // ESTADÍSTICAS GLOBALES
    // ============================================

    @GetMapping("/estadisticas")
    public ResponseEntity<?> getEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRestaurantes", restauranteService.buscarTodos().size());

        stats.put("totalPerfiles", perfilService.getAllPerfiles().size());
        stats.put("totalModulos", moduloService.getAllModulos().size());

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
        return superAdminService.getSuperAdminById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/super-admins")
    public ResponseEntity<?> createSuperAdmin(@RequestBody SuperAdmin superAdmin) {
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isMaster = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_MASTER"));

            if (!isMaster) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Acceso denegado: Solo el MASTER puede crear nuevos SuperAdmins.");
            }

            System.out.println("Creando nuevo SuperAdmin: " + superAdmin.getEmail());

            SuperAdmin created = superAdminService.createSuperAdmin(superAdmin);

            // bitacoraService.logCreacion(0, "super_admin", created.getIdSuperAdmin(),
            // "SuperAdmin creado: " + created.getEmail() + " - Rol: " + created.getRol());

            created.setPassword(null);

            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/super-admins/{id}")
    public ResponseEntity<?> updateSuperAdmin(@PathVariable Integer id, @RequestBody SuperAdmin superAdmin) {
        try {
            if (superAdmin.getPassword() != null && !superAdmin.getPassword().isEmpty()) {
                System.out.println("Actualizando contraseña para SuperAdmin ID: " + id);
            }

            Optional<SuperAdmin> updated = superAdminService.updateSuperAdmin(id, superAdmin);

            if (updated.isPresent()) {
                SuperAdmin result = updated.get();
                // No devolver la contraseña en la respuesta
                result.setPassword(null);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
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
        return ResponseEntity.ok(perfilService.getAllPerfiles());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRolById(@PathVariable Integer id) {
        Optional<Perfil> perfilOpt = perfilService.getPerfilById(id);
        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
        Perfil perfil = perfilOpt.get();
        if (!perfil.getNombrePerfil().startsWith("SUPERADMIN_")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Este no es un rol de SuperAdmin");
        }
        return ResponseEntity.ok(perfil);
    }

    @PostMapping("/roles")
    public ResponseEntity<?> createRol(@RequestBody Perfil perfil) {
        if (perfil.getNombrePerfil() == null || perfil.getNombrePerfil().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre del rol es requerido");
        }
        if (!perfil.getNombrePerfil().startsWith("SUPERADMIN_")) {
            perfil.setNombrePerfil("SUPERADMIN_" + perfil.getNombrePerfil());
        }
        perfil.setEstado(1);
        Perfil saved = perfilService.savePerfil(perfil);

        if (perfil.getNombrePerfil().contains("ADMIN") || perfil.getNombrePerfil().contains("Administrador")) {
            try {
                List<Modulo> todosModulos = moduloService.getAllModulos();
                for (Modulo modulo : todosModulos) {
                    if (!accesoService.existeAcceso(modulo.getIdModulo(), saved.getIdPerfil())) {
                        Acceso nuevoAcceso = new Acceso();
                        nuevoAcceso.setIdPerfil(saved.getIdPerfil());
                        nuevoAcceso.setIdModulo(modulo.getIdModulo());
                        nuevoAcceso.setEstado(1);
                        accesoService.saveAcceso(nuevoAcceso);
                    }
                }
                System.out.println("✅ Todos los módulos asignados automáticamente al rol administrativo: "
                        + perfil.getNombrePerfil());
            } catch (Exception e) {
                System.err.println("⚠️ Error al asignar módulos automáticamente: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> updateRol(@PathVariable Integer id, @RequestBody Perfil perfil) {
        Optional<Perfil> existingOpt = perfilService.getPerfilById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
        Perfil existing = existingOpt.get();
        if (!existing.getNombrePerfil().startsWith("SUPERADMIN_")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No puedes modificar roles que no son de SuperAdmin");
        }
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
        return moduloService.getModuloById(id).map(ResponseEntity::ok)
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
        List<Acceso> accesos = accesoService.getAllAccesos().stream().filter(a -> a.getIdPerfil().equals(idRol))
                .collect(Collectors.toList());
        List<Integer> idsModulos = accesos.stream().map(Acceso::getIdModulo).collect(Collectors.toList());
        return ResponseEntity.ok(idsModulos);
    }

    @PostMapping("/roles/{idRol}/permisos")
    public ResponseEntity<?> asignarPermisos(@PathVariable Integer idRol, @RequestBody List<Integer> idsModulos) {
        System.out.println("Asignando permisos al rol " + idRol + ": " + idsModulos);
        Optional<Perfil> perfilOpt = perfilService.getPerfilById(idRol);
        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
        List<Acceso> accesosAnteriores = accesoService.getAllAccesos().stream()
                .filter(a -> a.getIdPerfil().equals(idRol)).collect(Collectors.toList());
        for (Acceso acceso : accesosAnteriores) {
            accesoService.deleteAcceso(acceso.getIdAcceso());
        }
        for (Integer idModulo : idsModulos) {
            Acceso nuevoAcceso = new Acceso();
            nuevoAcceso.setIdPerfil(idRol);
            nuevoAcceso.setIdModulo(idModulo);
            nuevoAcceso.setEstado(1);
            accesoService.saveAcceso(nuevoAcceso);
        }
        return ResponseEntity
                .ok(Map.of("mensaje", "Permisos asignados correctamente", "totalPermisos", idsModulos.size()));
    }

    // ============================================
    // GESTIÓN DE RESTAURANTES
    // ============================================
    @GetMapping("/restaurantes")
    public ResponseEntity<List<Restaurante>> getAllRestaurantes() {
        return ResponseEntity.ok(restauranteService.buscarTodos());
    }

    @GetMapping("/restaurantes/{id}")
    public ResponseEntity<?> getRestauranteById(@PathVariable Integer id) {
        return restauranteService.buscarId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @Autowired
    private web.Regional_Api.service.jpa.SucursalesService sucursalesService;

    @PostMapping("/restaurantes")
    public ResponseEntity<?> createRestaurante(@RequestBody Restaurante restaurante) {
        try {
            // 1. Validar duplicados (RUC)
            if (restauranteService.existsByRuc(restaurante.getRuc())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"El RUC " + restaurante.getRuc() + " ya está registrado en el sistema.\"}");
            }

            restaurante.setEstado(1);
            Restaurante saved = restauranteService.guardar(restaurante);

            // bitacoraService.logCreacion(0, "restaurante", saved.getId_restaurante(),
            // "Restaurante creado: " + saved.getRazon_social() + " - RUC: " +
            // saved.getRuc());

            // 2. Crear Sucursal Principal
            web.Regional_Api.entity.Sucursales sucursalPrincipal = new web.Regional_Api.entity.Sucursales();
            sucursalPrincipal.setIdRestaurante(saved.getId_restaurante());
            sucursalPrincipal.setNombre("Sucursal Principal");

            // 🛡️ Protección contra dirección Nula (Causa común de error 500)
            String direccion = saved.getDireccion_principal();
            if (direccion == null || direccion.trim().isEmpty()) {
                direccion = "Dirección Pendiente de Actualizar";
            }
            sucursalPrincipal.setDireccion(direccion);

            sucursalPrincipal.setEstado(1);
            sucursalesService.guardar(sucursalPrincipal);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            System.err.println("❌ Error al crear restaurante: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error interno: " + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/restaurantes/{id}")
    public ResponseEntity<?> updateRestaurante(@PathVariable Integer id, @RequestBody Restaurante restaurante) {
        if (restauranteService.buscarId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante no encontrado");
        }
        restaurante.setId_restaurante(id);
        Restaurante updated = restauranteService.guardar(restaurante);

        // bitacoraService.logActualizacion(0, "restaurante", id,
        // "Restaurante actualizado: " + updated.getRazon_social());

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/restaurantes/{id}")
    public ResponseEntity<?> deleteRestaurante(@PathVariable Integer id) {
        if (restauranteService.buscarId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante no encontrado");
        }
        restauranteService.eliminar(id);

        // bitacoraService.logEliminacion(0, "restaurante", id, "Restaurante eliminado
        // ID: " + id);

        return ResponseEntity.ok("Restaurante eliminado correctamente");
    }

    // ============================================
    // GESTIÓN DE USUARIOS (Clientes / Dueños)
    // ============================================
    @PostMapping("/usuarios")
    public ResponseEntity<?> createUsuario(@RequestBody Map<String, Object> request) {
        try {
            // Extract fields
            String nombreUsuario = (String) request.get("nombreUsuario");
            String apellidos = (String) request.get("apellidos");
            String dniUsuario = (String) request.get("dniUsuario");
            String telefono = (String) request.get("telefono");
            String nombreUsuarioLogin = (String) request.get("nombreUsuarioLogin");
            String contrasena = (String) request.get("contrasena");

            Object rolIdObj = request.get("rolId");
            Integer rolId = null;
            if (rolIdObj instanceof Number) {
                rolId = ((Number) rolIdObj).intValue();
            } else if (rolIdObj instanceof String) {
                try {
                    rolId = Integer.parseInt((String) rolIdObj);
                } catch (NumberFormatException e) {
                    // ignore
                }
            }

            Object idSucursalObj = request.get("idSucursal");
            Integer idSucursal = null;
            if (idSucursalObj instanceof Number) {
                idSucursal = ((Number) idSucursalObj).intValue();
            } else if (idSucursalObj instanceof String) {
                try {
                    idSucursal = Integer.parseInt((String) idSucursalObj);
                } catch (NumberFormatException e) {
                    // ignore
                }
            }

            // Validate mandatory fields
            if (nombreUsuario == null || apellidos == null || dniUsuario == null ||
                    nombreUsuarioLogin == null || contrasena == null || rolId == null || idSucursal == null) {
                return ResponseEntity.badRequest().body("{\"error\": \"Faltan datos obligatorios\"}");
            }

            if (usuarioService.getUsuarioByLogin(nombreUsuarioLogin).isPresent()) {
                return ResponseEntity.badRequest().body("{\"error\": \"El nombre de usuario ya existe\"}");
            }

            if (idSucursal != null) {

                java.util.List<web.Regional_Api.entity.Sucursales> sucursales = sucursalesService
                        .buscarTodosPorRestaurante(idSucursal);
                if (sucursales != null && !sucursales.isEmpty()) {

                    idSucursal = sucursales.get(0).getIdSucursal();
                } else {
                    System.out.println("⚠️ No se encontraron sucursales para el Restaurante ID: " + idSucursal);
                }
            }

            // Create Usuario
            Usuarios newUser = new Usuarios();
            newUser.setNombreUsuario(nombreUsuario);
            newUser.setApellidos(apellidos);
            newUser.setDniUsuario(dniUsuario);
            newUser.setTelefono(telefono);
            newUser.setNombreUsuarioLogin(nombreUsuarioLogin);
            newUser.setRolId(rolId);
            newUser.setIdSucursal(idSucursal);
            newUser.setEstado(1);
            newUser.setFechaCreacion(java.time.LocalDateTime.now());

            // Usar SHA-256 en formato HEXADECIMAL para consistencia con el login
            String hashedPassword = hashPasswordHex(contrasena);
            newUser.setContrasena(hashedPassword);

            // Save
            Usuarios savedUser = usuarioService.saveUsuarios(newUser);

            // bitacoraService.logCreacion(0, "usuario", savedUser.getIdUsuario(),
            // "Usuario creado: " + savedUser.getNombreUsuarioLogin());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear usuario: " + e.getMessage() + "\"}");
        }
    }

    // ============================================
    // CREAR ADMINISTRADOR CON TODOS LOS ACCESOS
    // ============================================
    /**
     * Endpoint para crear un administrador desde el SuperAdmin
     * Automáticamente asigna TODOS los módulos/accesos disponibles
     */
    @PostMapping("/crear-administrador")
    public ResponseEntity<?> crearAdministrador(@RequestBody Map<String, Object> request) {
        try {
            // Verificar que sea MASTER
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isMaster = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_MASTER"));

            if (!isMaster) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Acceso denegado: Solo el MASTER puede crear administradores");
            }

            String nombreUsuario = (String) request.get("nombreUsuario");
            String apellidos = (String) request.get("apellidos");
            String dniUsuario = (String) request.get("dniUsuario");
            String telefono = (String) request.get("telefono");
            String nombreUsuarioLogin = (String) request.get("nombreUsuarioLogin");
            String contrasena = (String) request.get("contrasena");

            Object rolIdObj = request.get("rolId");
            Integer rolId = null;
            if (rolIdObj instanceof Number) {
                rolId = ((Number) rolIdObj).intValue();
            } else if (rolIdObj instanceof String) {
                try {
                    rolId = Integer.parseInt((String) rolIdObj);
                } catch (NumberFormatException e) {
                }
            }

            Object idSucursalObj = request.get("idSucursal");
            Integer idSucursal = null;
            if (idSucursalObj instanceof Number) {
                idSucursal = ((Number) idSucursalObj).intValue();
            } else if (idSucursalObj instanceof String) {
                try {
                    idSucursal = Integer.parseInt((String) idSucursalObj);
                } catch (NumberFormatException e) {

                }
            }

            if (nombreUsuario == null || apellidos == null || dniUsuario == null ||
                    nombreUsuarioLogin == null || contrasena == null || rolId == null || idSucursal == null) {
                return ResponseEntity.badRequest().body("{\"error\": \"Faltan datos obligatorios\"}");
            }
            if (usuarioService.getUsuarioByLogin(nombreUsuarioLogin).isPresent()) {
                return ResponseEntity.badRequest().body("{\"error\": \"El nombre de usuario ya existe\"}");
            }

            if (idSucursal != null) {
                java.util.List<web.Regional_Api.entity.Sucursales> sucursales = sucursalesService
                        .buscarTodosPorRestaurante(idSucursal);
                if (sucursales != null && !sucursales.isEmpty()) {
                    idSucursal = sucursales.get(0).getIdSucursal();
                } else {
                    System.out.println("⚠️ No se encontraron sucursales para el Restaurante ID: " + idSucursal);
                }
            }

            // Create Usuario
            Usuarios newUser = new Usuarios();
            newUser.setNombreUsuario(nombreUsuario);
            newUser.setApellidos(apellidos);
            newUser.setDniUsuario(dniUsuario);
            newUser.setTelefono(telefono);
            newUser.setNombreUsuarioLogin(nombreUsuarioLogin);
            newUser.setRolId(rolId);
            newUser.setIdSucursal(idSucursal);
            newUser.setEstado(1);
            newUser.setFechaCreacion(java.time.LocalDateTime.now());

            // Usar SHA-256 en formato HEXADECIMAL para consistencia con el login
            String hashedPassword = hashPasswordHex(contrasena);
            newUser.setContrasena(hashedPassword);

            Usuarios savedUser = usuarioService.saveUsuarios(newUser);

            final Integer finalRolId = rolId;
            try {
                List<Modulo> todosModulos = moduloService.getAllModulos();

                List<Acceso> accesosAnteriores = accesoService.getAllAccesos().stream()
                        .filter(a -> a.getIdPerfil().equals(finalRolId))
                        .collect(Collectors.toList());
                for (Acceso acceso : accesosAnteriores) {
                    accesoService.deleteAcceso(acceso.getIdAcceso());
                }

                // Asignar todos los módulos disponibles
                for (Modulo modulo : todosModulos) {
                    Acceso nuevoAcceso = new Acceso();
                    nuevoAcceso.setIdPerfil(finalRolId);
                    nuevoAcceso.setIdModulo(modulo.getIdModulo());
                    nuevoAcceso.setEstado(1);
                    accesoService.saveAcceso(nuevoAcceso);
                }

                System.out.println("✅ Administrador creado con éxito: " + nombreUsuarioLogin);
                System.out.println("✅ Se asignaron " + todosModulos.size() + " módulos/accesos automáticamente");
            } catch (Exception e) {
                System.err.println("⚠️ Error al asignar accesos: " + e.getMessage());
                e.printStackTrace();

            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear administrador: " + e.getMessage() + "\"}");
        }
    }
    // ============================================
    // LISTAR, ACTUALIZAR Y ELIMINAR USUARIOS
    // ============================================

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuarios>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Integer id, @RequestBody Map<String, Object> request) {
        try {
            Optional<Usuarios> usuarioOpt = usuarioService.getUsuarioById(id);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Usuarios usuario = usuarioOpt.get();

            // Actualizar campos si vienen en el request
            if (request.get("nombreUsuario") != null) {
                usuario.setNombreUsuario((String) request.get("nombreUsuario"));
            }
            if (request.get("apellidos") != null) {
                usuario.setApellidos((String) request.get("apellidos"));
            }
            if (request.get("nombreUsuarioLogin") != null) {
                usuario.setNombreUsuarioLogin((String) request.get("nombreUsuarioLogin"));
            }
            if (request.get("dniUsuario") != null) {
                usuario.setDniUsuario((String) request.get("dniUsuario"));
            }
            if (request.get("telefono") != null) {
                usuario.setTelefono((String) request.get("telefono"));
            }
            if (request.get("idSucursal") != null) {
                Object idSucursalObj = request.get("idSucursal");
                if (idSucursalObj instanceof Number) {
                    usuario.setIdSucursal(((Number) idSucursalObj).intValue());
                }
            }

            // Si viene nueva contraseña, encriptarla
            if (request.get("contrasenaUsuario") != null) {
                String nuevaContrasena = (String) request.get("contrasenaUsuario");
                if (!nuevaContrasena.isEmpty()) {
                    String hashedPassword = hashPasswordHex(nuevaContrasena);
                    usuario.setContrasena(hashedPassword);
                }
            }

            Usuarios updated = usuarioService.saveUsuarios(usuario);

            // bitacoraService.logActualizacion(0, "usuario", id,
            // "Usuario actualizado: " + updated.getNombreUsuarioLogin());

            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al actualizar usuario: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        try {
            if (usuarioService.getUsuarioById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            usuarioService.deleteUsuario(id);

            // bitacoraService.logEliminacion(0, "usuario", id, "Usuario eliminado ID: " +
            // id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al eliminar usuario: " + e.getMessage() + "\"}");
        }
    }

    // ============================================
    // BITÁCORA / AUDITORÍA
    // ============================================

    @Autowired
    private web.Regional_Api.service.jpa.BitacoraService bitacoraService;

    @GetMapping("/bitacora")
    public ResponseEntity<List<web.Regional_Api.entity.BitacoraAccion>> getBitacora() {
        return ResponseEntity.ok(bitacoraService.obtenerTodos());
    }

    @GetMapping("/bitacora/usuario/{idUsuario}")
    public ResponseEntity<List<web.Regional_Api.entity.BitacoraAccion>> getBitacoraPorUsuario(
            @PathVariable Integer idUsuario) {
        return ResponseEntity.ok(bitacoraService.obtenerPorUsuario(idUsuario));
    }

    @GetMapping("/bitacora/tabla/{tabla}")
    public ResponseEntity<List<web.Regional_Api.entity.BitacoraAccion>> getBitacoraPorTabla(
            @PathVariable String tabla) {
        return ResponseEntity.ok(bitacoraService.obtenerPorTabla(tabla));
    }

    @GetMapping("/bitacora/estadisticas")
    public ResponseEntity<?> getEstadisticasBitacora() {
        return ResponseEntity.ok(bitacoraService.estadisticasPorAccion());
    }

    // ========== TOGGLE ESTADO ==========

    // Toggle estado SuperAdmin
    @PatchMapping("/super-admins/{id}/estado")
    public ResponseEntity<?> toggleEstadoSuperAdmin(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> body) {
        try {
            SuperAdmin sa = superAdminService.getSuperAdminById(id).orElse(null);
            if (sa == null) {
                return ResponseEntity.notFound().build();
            }
            sa.setEstado(body.get("estado"));
            SuperAdmin updated = superAdminService.createSuperAdmin(sa);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Toggle estado Usuario
    @PatchMapping("/usuarios/{id}/estado")
    public ResponseEntity<?> toggleEstadoUsuario(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> body) {
        try {
            Usuarios usuario = usuarioService.getUsuarioById(id).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            usuario.setEstado(body.get("estado"));
            Usuarios updated = usuarioService.saveUsuarios(usuario);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}