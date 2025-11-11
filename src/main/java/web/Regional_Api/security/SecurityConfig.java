package web.Regional_Api.security;

// ... (Otras importaciones)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// Importaciones clave para CORS
import org.springframework.web.cors.CorsConfiguration; 
import org.springframework.web.cors.CorsConfigurationSource; // Usaremos esta
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // Cambiado de CorsFilter a CorsConfigurationSource
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permite todas las peticiones desde cualquier origen
        configuration.setAllowCredentials(true); 
        configuration.addAllowedOriginPattern("*"); // Orígenes (ej. http://localhost:4200, *)
        configuration.addAllowedHeader("*");        // Headers (incluye Content-Type)
        configuration.addAllowedMethod("*");        // Métodos (POST, GET, PUT, DELETE)
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
            // 1. Aplicar CORS usando la fuente de configuración
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. Deshabilitar CSRF
            .csrf(csrf -> csrf.disable()) 
            
            // 3. Configurar sesión sin estado
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
            
            // 4. Configurar Autorización
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/usuarios/registro", 
                    "/usuarios/login", 
                    "/actuator/**",
                    "/modulos", "/modulos/**",
                    "/perfiles", "/perfiles/**",
                    "/accesos", "/accesos/**"
                ).permitAll()
                    .anyRequest().permitAll()  // Permite temporalmente todos los endpoints sin autenticación
            )
            
            // 5. Añadir el filtro JWT
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); 

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}