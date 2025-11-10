package web.Regional_Api.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.service.jpa.UsuarioService;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        // 1. EXCLUSION (BYPASS) FOR PUBLIC ROUTES
        // If the request targets registration or login endpoints, skip JWT validation.
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/usuarios/registro") || requestURI.endsWith("/usuarios/login") || requestURI.endsWith("/usuarios/validar-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Lógica de Extracción y Autenticación del Token (para rutas protegidas)
        extractToken(request)
            .ifPresent(this::authenticateFromToken);

        // 3. Continuar la cadena de filtros
        // Spring Security ahora evaluará el SecurityContextHolder y la configuración
        // (anyRequest().authenticated())
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }

    private void authenticateFromToken(String token) {
        try {
            // Validate token first
            if (jwtUtil.validateToken(token)) {
                // extract subject (we store nombreUsuarioLogin as subject)
                String nombreUsuarioLogin = jwtUtil.extractEmail(token);

                // Validate token and obtain user using the token (UsuarioService expects token)
                Usuarios usuario = usuarioService.validarToken(token);

                if (usuario != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            usuario.getNombreUsuarioLogin(), null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (RuntimeException ex) {
            logger.debug("JWT validation failed", ex);
            SecurityContextHolder.clearContext();
        }
    }
}