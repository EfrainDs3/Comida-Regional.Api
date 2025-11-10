package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Acceso;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso, Integer> {
	boolean existsByIdModuloAndIdPerfilAndEstado(Integer idModulo, Integer idPerfil, Integer estado);
}
