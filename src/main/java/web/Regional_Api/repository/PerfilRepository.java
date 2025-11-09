package web.Regional_Api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
	Optional<Perfil> findByNombrePerfil(String nombrePerfil);
}
