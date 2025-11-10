package web.Regional_Api.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.service.jpa.UsuarioService;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        // 1. **EXCLUSIÓN (BYPASS) PARA RUTAS PÚBLICAS**
        // Si la URI es la de registro o login, saltamos la validación del filtro JWT.
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/usuarios/registro") || requestURI.equals("/usuarios/login")) {
            filterChain.doFilter(request, response);
            return; // Permite el paso inmediato
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
            // Usa JwtUtil para validar el token
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);  // Extrae el email del token
                // Asegúrate de que 'validarToken' devuelve un objeto que implementa UserDetails,
                // o al menos que 'Usuarios' tiene un método getAuthorities() si se usa en producción.
                Usuarios usuario = usuarioService.validarToken(email);  // Busca el usuario por el email

                if (usuario != null) {
                    // Crea el token de autenticación. Se asume que getNombreUsuarioLogin() es el principal.
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            usuario.getNombreUsuarioLogin(), null, Collections.emptyList()); 
                    
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (RuntimeException ex) {
            // En un entorno de producción, es mejor dejar que Spring Security
            // o un filtro posterior manejen la excepción para devolver un 401.
            logger.debug("JWT validation failed", ex);
            SecurityContextHolder.clearContext();
        }
    }
}