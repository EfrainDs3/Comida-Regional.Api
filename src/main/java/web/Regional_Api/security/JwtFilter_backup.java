package web.Regional_Api.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.Regional_Api.entity.Registros;
import web.Regional_Api.repository.RegistrosRepository;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.repository.UsuarioRepository;

// Backup file - Disabled by removing @Component
// @Component 
public class JwtFilter_backup extends GenericFilter {

    @Autowired
    private RegistrosRepository registrosRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI();

        // NO FILTRAR los endpoints de autenticación pública
        if (path.equals("/restful/superadmin/login") ||
                path.startsWith("/restful/superadmin/auth/")) {
            chain.doFilter(req, res);
            return;
        }

        // PERMITIR endpoints de SuperAdmin con token válido
        if (path.startsWith("/restful/superadmin/")) {
            String token = extractToken(request);

            if (token != null && jwtUtil.validarToken(token)) {
                // Token válido, establecer autenticación y permitir acceso
                String username = jwtUtil.extraerClienteId(token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
                chain.doFilter(req, res);
                return;
            } else {
                // Token inválido o no presente
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido o no presente");
                return;
            }
        }

        // Para otros endpoints, aplicar lógica normal
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
            // 2. Try New Admin Authentication (Usuarios table)
            else if (jwtUtil.validarToken(token)) {
                String username = jwtUtil.extraerClienteId(token);
                Optional<Usuarios> usuarioOpt = usuarioRepository
                        .findByNombreUsuarioLogin(username);

                if (usuarioOpt.isPresent()) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,
                            null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        chain.doFilter(req, res);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}