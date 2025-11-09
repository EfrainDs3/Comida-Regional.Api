package web.Regional_Api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desactiva CSRF para servicios RESTful
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin estado
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/usuarios/registro", "/usuarios/login", "/actuator/**").permitAll() // Endpoints públicos
                .requestMatchers("/modulos", "/modulos/**",
                    "/perfiles", "/perfiles/**",
                    "/accesos", "/accesos/**").permitAll()  // Otras rutas públicas
                .anyRequest().authenticated())  // Requiere autenticación para el resto
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
