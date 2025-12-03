package web.Regional_Api.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import web.Regional_Api.entity.Registros;
import web.Regional_Api.repository.RegistrosRepository;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.repository.UsuarioRepository;
import web.Regional_Api.entity.SuperAdmin;
import web.Regional_Api.repository.SuperAdminRepository;

@Component
public class JwtFilter extends GenericFilter {
    @Autowired
    private RegistrosRepository registrosRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            // 1. Try Legacy Authentication (Registros table)
            Optional<Registros> match = registrosRepository.findAll()
                    .stream()
                    .filter(r -> token.equals(r.getAccess_token()))
                    .findFirst();

            if (match.isPresent()) {
                String clienteId = match.get().getid_usuario();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(clienteId,
                        null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            // 2. Try New Admin Authentication (Usuarios table or SuperAdmin table)
            else if (jwtUtil.validarToken(token)) {
                String usernameOrEmail = jwtUtil.extraerClienteId(token);

                // Primero buscar en Usuarios
                Optional<Usuarios> usuarioOpt = usuarioRepository
                        .findByNombreUsuarioLogin(usernameOrEmail);

                if (usuarioOpt.isPresent()) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usernameOrEmail,
                            null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    // Si no es usuario, buscar en SuperAdmins (por email)
                    Optional<SuperAdmin> superAdminOpt = superAdminRepository.findByEmail(usernameOrEmail);

                    if (superAdminOpt.isPresent()) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                usernameOrEmail,
                                null, Collections.emptyList());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        }

        chain.doFilter(req, res);
    }
}
