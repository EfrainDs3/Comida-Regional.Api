package web.Regional_Api.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.service.jpa.UsuarioService;

@Component
public class JwtFilter extends GenericFilterBean {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        httpServletRequest(request)
            .map(req -> req.getHeader(HttpHeaders.AUTHORIZATION))
            .filter(header -> header.startsWith("Bearer "))
            .map(header -> header.substring(7))
            .ifPresent(this::authenticateFromToken);

        chain.doFilter(request, response);
    }

    private void authenticateFromToken(String token) {
        try {
            // Usa JwtUtil para validar el token
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);  // Extrae el email del token
                Usuarios usuario = usuarioService.validarToken(email);  // Busca el usuario por el email

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

    private java.util.Optional<HttpServletRequest> httpServletRequest(ServletRequest request) {
        if (request instanceof HttpServletRequest httpRequest) {
            return java.util.Optional.of(httpRequest);
        }
        return java.util.Optional.empty();
    }
}
