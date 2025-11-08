package web.Regional_Api.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.repository.UsuarioRepository;
import web.Regional_Api.security.JwtUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario Usuario a registrar
     * @return Usuario registrado con su access token generado
     * @throws RuntimeException si el email ya existe
     */

    public Usuarios registrarUsuario(Usuarios usuario) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado en el sistema");
        }
        
        // La contraseña ya se cifra automáticamente en el setter con SHA-256
        // Generar el access token usando JWT
        String token = jwtUtil.generateToken(usuario.getEmail());
        usuario.setAccessToken(token);
        
        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    /**
     * Realiza el login de un usuario
     * @param email Email del usuario
     * @param contraseña Contraseña en texto plano (se cifrará para comparar)
     * @return Usuario con su información si las credenciales son correctas
     * @throws RuntimeException si las credenciales son incorrectas
     */
    public Usuarios login(String email, String contraseña) {
        // Buscar el usuario por email
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }
        
        Usuarios usuario = usuarioOpt.get();
        
        // Cifrar la contraseña ingresada para compararla
        Usuarios temp = new Usuarios();
        temp.setContraseña(contraseña);
        String contraseñaCifrada = temp.getContraseña();
        
        // Verificar si la contraseña coincide
        if (!usuario.getContraseña().equals(contraseñaCifrada)) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }
        
        // Si no tiene token, generar uno nuevo
        if (usuario.getAccessToken() == null || usuario.getAccessToken().isEmpty()) {
            String token = jwtUtil.generateToken(usuario.getEmail());
            usuario.setAccessToken(token);
            usuarioRepository.save(usuario);
        }
        
        return usuario;
    }

    /**
     * Valida un access token
     * @param token Token a validar
     * @return Usuario asociado al token si es válido
     * @throws RuntimeException si el token es inválido
     */
    public Usuarios validarToken(String token) {
        // Primero validar el token con JWT
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }
        
        // Extraer el email del token
        String email = jwtUtil.extractEmail(token);
        
        // Buscar el usuario en la base de datos
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByEmail(email);
        
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
    public Optional<Usuarios> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca un usuario por su email
     * @param email Email del usuario
     * @return Usuario si existe
     */
    public Optional<Usuarios> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
