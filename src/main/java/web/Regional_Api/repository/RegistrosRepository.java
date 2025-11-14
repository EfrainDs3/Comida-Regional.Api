package web.Regional_Api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.Regional_Api.entity.Registros;

public interface RegistrosRepository extends JpaRepository<Registros, Integer>{
	@Query("SELECT r FROM Registros r WHERE r.access_token = :token")
	Optional<Registros> findByAccessToken(@Param("token") String token);

	@Query("SELECT r FROM Registros r WHERE r.id_usuario = :idUsuario")
	Optional<Registros> findByIdUsuario(@Param("idUsuario") String idUsuario);
    
}