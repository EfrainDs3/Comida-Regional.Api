package web.Regional_Api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
   
    /**
     * Busca un usuario por su email
     * @param email Email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuarios> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con ese email
     * @param email Email a verificar
     * @return true si existe, false si no
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca un usuario por su access token
     * @param accessToken Token de acceso
     * @return Optional con el usuario si existe
     */
    Optional<Usuarios> findByAccessToken(String accessToken);
}
