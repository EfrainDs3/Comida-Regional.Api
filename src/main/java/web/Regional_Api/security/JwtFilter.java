package web.Regional_Api.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import web.Regional_Api.entity.Registros;
import web.Regional_Api.repository.RegistrosRepository;

@Component
public class JwtFilter extends GenericFilterBean {

    @Autowired
    private RegistrosRepository registrosRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                    FilterChain chain) throws IOException,
                    ServletException{
        HttpServletRequest request = (HttpServletRequest) req;
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            Optional<Registros> match = registrosRepository
                .findAll().stream()
                .filter(r->token.equals(r.getAccess_token()))
                .findFirst(); 

            if(match.isPresent()){
                String usuarioId = match.get().getUsuario_id();
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(usuarioId, 
                        null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
<<<<<<< HEAD
        } catch (RuntimeException ex) {
            logger.debug("JWT validation failed", ex);
            SecurityContextHolder.clearContext();
        }
    }

    private void authenticateDeveloper(String token) {
        // First, extract the subject from the token (should be the cliente/usuario identifier)
        String usuarioId = jwtUtil.extractSubject(token);
        // Then check that the token is still valid in DB (not revoked) by finding the record by access token
        Optional<Registros> registro = registrosService.buscarPorAccessToken(token);
        if (registro.isPresent()
            && registro.get().getUsuario_id() != null
            && registro.get().getUsuario_id().equals(usuarioId)
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    usuarioId,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_DEV")));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    private void authenticateUser(String token) {
        Usuarios usuario = usuarioService.validarToken(token);
        if (usuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    usuario.getNombreUsuarioLogin(),
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(auth);
=======
>>>>>>> f3962c3143b401d61ac21cb62ba9db512927d280
        }
        chain.doFilter(req, res);
    }
}