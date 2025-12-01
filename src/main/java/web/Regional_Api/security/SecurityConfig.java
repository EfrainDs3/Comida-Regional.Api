package web.Regional_Api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración base de seguridad
 * 
 * Proporciona beans compartidos por toda la aplicación:
 * - BCryptPasswordEncoder para encriptación de contraseñas
 * - CorsConfigurationSource para configuración CORS global
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Bean para encriptación de contraseñas usando BCrypt
     * 
     * BCrypt es más seguro que SHA-256 porque:
     * - Incluye salt automático
     * - Es más lento (dificulta ataques de fuerza bruta)
     * - Es adaptativo (puede aumentar complejidad en el futuro)
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración CORS global
     * 
     * Permite peticiones desde cualquier origen durante desarrollo
     * En producción, deberías restringir los orígenes permitidos
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Usar allowedOriginPatterns en lugar de allowedOrigins cuando allowCredentials
        // = true
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Permitir credenciales
        configuration.setAllowCredentials(true);

        // Aplicar configuración a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
