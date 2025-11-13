package web.Regional_Api.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.repository.UsuarioRepository;
import web.Regional_Api.security.JwtUtil;
import web.Regional_Api.service.IUsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario Usuario a registrar
     * @return Usuario registrado con su access token generado
     * @throws RuntimeException si el email ya existe
     */

    @Override
    public Usuarios registrarUsuario(Usuarios usuario) {
        // Verificar si el nombre de usuario de login ya existe
        if (usuarioRepository.existsByNombreUsuarioLogin(usuario.getNombreUsuarioLogin())) {
            throw new RuntimeException("El nombre de usuario de login ya está registrado en el sistema");
        }

        // Cifrar la contraseña antes de guardar
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Generar token JWT
        String token = jwtUtil.generateToken(usuario.getNombreUsuarioLogin());
        usuario.setAccessToken(token);

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    /**
     * Realiza el login de un usuario
     * @param email
     * @param contrasena 
     * @return 
     * @throws RuntimeException 
     */
    @Override
    public Usuarios login(String nombreUsuarioLogin, String contrasena) {
        // Buscar el usuario por su nombre de usuario de login
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByNombreUsuarioLogin(nombreUsuarioLogin);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario o contrasena incorrectos");
        }

        Usuarios usuario = usuarioOpt.get();

        // Cifrar la contrasena ingresada para compararla
        Usuarios temp = new Usuarios();
        temp.setContrasena(contrasena);
        String contrasenaCifrada = temp.getContrasena();

        // Verificar si la contrasena coincide
        if (!usuario.getContrasena().equals(contrasenaCifrada)) {
            throw new RuntimeException("Usuario o contrasena incorrectos");
        }

        // Generar token JWT y asignarlo de forma transient (no se persiste)
        String token = jwtUtil.generateToken(usuario.getNombreUsuarioLogin());
        usuario.setAccessToken(token);

        // Actualizar ultimo_login en la BD
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return usuario;
    }

    /**
     * Valida un access token
     * @param token Token a validar
     * @return Usuario asociado al token si es válido
     * @throws RuntimeException si el token es inválido
     */
    @Override
    public Usuarios validarToken(String token) {
        // Primero validar el token con JWT
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }
        
        // Extraer el email del token
        // En el token guardamos como subject el nombreUsuarioLogin
        String tokenType = Optional.ofNullable(jwtUtil.extractTokenType(token)).orElse("USER");
        if (!"USER".equalsIgnoreCase(tokenType)) {
            throw new RuntimeException("Token no corresponde a un usuario");
        }

        String nombreUsuarioLogin = jwtUtil.extractSubject(token);

        // Buscar el usuario en la base de datos por nombreUsuarioLogin
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByNombreUsuarioLogin(nombreUsuarioLogin);
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        return usuarioOpt.get();
    }

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Usuario si existe
     */
    @Override
    public Optional<Usuarios> getUsuarioById(Integer id) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        return usuarioRepository.findById(id);
    }

    /**
     * Busca un usuario por su nombreUsuarioLogin
     * @param nombreUsuarioLogin Nombre de usuario
     * @return Usuario si existe
     */
    @Override
    public Optional<Usuarios> getUsuarioByLogin(String nombreUsuarioLogin) {
        return usuarioRepository.findByNombreUsuarioLogin(nombreUsuarioLogin);
    }

    @Override
    @SuppressWarnings("null")
    public Optional<Usuarios> updateUsuario(Integer id, Usuarios usuario) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        Optional<Usuarios> existenteOpt = usuarioRepository.findById(id);
        if (existenteOpt.isEmpty()) {
            return Optional.empty();
        }

        Usuarios existente = existenteOpt.get();

        if (usuario.getNombreUsuarioLogin() != null
                && !usuario.getNombreUsuarioLogin().equals(existente.getNombreUsuarioLogin())) {
            if (usuarioRepository.existsByNombreUsuarioLogin(usuario.getNombreUsuarioLogin())) {
                throw new RuntimeException("El nombre de usuario de login ya está registrado en el sistema");
            }
            existente.setNombreUsuarioLogin(usuario.getNombreUsuarioLogin());
        }

        if (usuario.getNombreUsuario() != null) {
            existente.setNombreUsuario(usuario.getNombreUsuario());
        }

        if (usuario.getApellidos() != null) {
            existente.setApellidos(usuario.getApellidos());
        }

        if (usuario.getDniUsuario() != null) {
            existente.setDniUsuario(usuario.getDniUsuario());
        }

        if (usuario.getTelefono() != null) {
            existente.setTelefono(usuario.getTelefono());
        }

        if (usuario.getContrasena() != null) {
            existente.setContrasena(usuario.getContrasena());
        }

        if (usuario.getRolId() != null) {
            existente.setRolId(usuario.getRolId());
        }

        if (usuario.getIdSucursal() != null) {
            existente.setIdSucursal(usuario.getIdSucursal());
        }

        if (usuario.getEstado() != null) {
            existente.setEstado(usuario.getEstado());
        }

        if (usuario.getUltimoLogin() != null) {
            existente.setUltimoLogin(usuario.getUltimoLogin());
        }

        return Optional.ofNullable(usuarioRepository.save(existente));
    }

    @Override
    public void deleteUsuario(Integer id) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
