package web.Regional_Api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
   
    // Métodos adaptados a la nueva tabla: buscamos por nombre de usuario de login
    Optional<Usuarios> findByNombreUsuarioLogin(String nombreUsuarioLogin);
    boolean existsByNombreUsuarioLogin(String nombreUsuarioLogin);
}
