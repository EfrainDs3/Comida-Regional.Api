package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import web.Regional_Api.entity.SuperAdmin;
import web.Regional_Api.repository.SuperAdminRepository;
import web.Regional_Api.service.ISuperAdminService;

@Service
public class SuperAdminService implements ISuperAdminService, UserDetailsService {

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<SuperAdmin> getAllSuperAdmins() {
        return superAdminRepository.findAll();
    }

    @Override
    public Optional<SuperAdmin> getSuperAdminById(Integer id) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        return superAdminRepository.findById(id);
    }

    @Override
    public SuperAdmin createSuperAdmin(SuperAdmin superAdmin) {
        if (superAdminRepository.existsByEmail(superAdmin.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Encode the password explicitly
        superAdmin.setPassword(passwordEncoder.encode(superAdmin.getPassword()));

        return superAdminRepository.save(superAdmin);
    }

    @Override
    public Optional<SuperAdmin> updateSuperAdmin(Integer id, SuperAdmin superAdmin) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        Optional<SuperAdmin> existenteOpt = superAdminRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            return Optional.empty();
        }

        SuperAdmin existente = existenteOpt.get();

        if (superAdmin.getNombres() != null) {
            existente.setNombres(superAdmin.getNombres());
        }

        if (superAdmin.getEmail() != null) {
            // Si cambia el email, verificar que no exista otro igual
            if (!existente.getEmail().equals(superAdmin.getEmail()) &&
                    superAdminRepository.existsByEmail(superAdmin.getEmail())) {
                throw new RuntimeException("El email ya está registrado");
            }
            existente.setEmail(superAdmin.getEmail());
        }

        if (superAdmin.getPassword() != null && !superAdmin.getPassword().isEmpty()) {
            // Encode the password explicitly
            existente.setPassword(passwordEncoder.encode(superAdmin.getPassword()));
        }

        if (superAdmin.getRol() != null) {
            existente.setRol(superAdmin.getRol());
        }

        if (superAdmin.getTokenLogin() != null) {
            existente.setTokenLogin(superAdmin.getTokenLogin());
        }

        if (superAdmin.getTokenExpiracion() != null) {
            existente.setTokenExpiracion(superAdmin.getTokenExpiracion());
        }

        return Optional.of(superAdminRepository.save(existente));
    }

    @Override
    public void deleteSuperAdmin(Integer id) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        if (!superAdminRepository.existsById(id)) {
            throw new RuntimeException("SuperAdmin no encontrado");
        }
        superAdminRepository.deleteById(id);
    }

    @Override
    public Optional<SuperAdmin> getSuperAdminByEmail(String email) {
        return superAdminRepository.findByEmail(email);
    }

    @Override
    public Optional<SuperAdmin> getSuperAdminByToken(String token) {
        return superAdminRepository.findByTokenLogin(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return superAdminRepository.findByEmail(username)
                .map(admin -> User.builder()
                        .username(admin.getEmail())
                        .password(admin.getPassword())
                        .roles(admin.getRol())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("SuperAdmin no encontrado con email: " + username));
    }

}
