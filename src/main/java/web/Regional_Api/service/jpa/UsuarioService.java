package web.Regional_Api.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.repository.UsuarioRepository;
import web.Regional_Api.service.IUsuarioService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Registra un nuevo usuario en el sistema
     * 
     * @param usuario Usuario a registrar
     * @return Usuario registrado
     * @throws RuntimeException si el email ya existe
     */

    @Override
    public Usuarios registrarUsuario(Usuarios usuario) {
        // Verificar si el nombre de usuario de login ya existe
        if (usuarioRepository.existsByNombreUsuarioLogin(usuario.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario de login ya está registrado en el sistema");
        }

        // Cifrar la contraseña antes de guardar
        // La entidad Usuarios ya se encarga de encriptar la contraseña con SHA-256 en
        // el método setContrasena
        // No es necesario usar BCrypt aquí

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuarios login(String nombreUsuarioLogin, String contrasena) {
        throw new UnsupportedOperationException("La lógica de login ahora está manejada por RegistrosService");
    }

    @Override
    public Usuarios validarToken(String token) {
        throw new UnsupportedOperationException("La validación de tokens ahora está manejada por RegistrosService");
    }

    /**
     * Busca un usuario por su ID
     * 
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
     * 
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

        if (usuario.getNombreUsuario() != null) {
            existente.setNombreUsuario(usuario.getNombreUsuario());
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
