package web.Regional_Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.Acceso;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso, Integer> {
	boolean existsByIdModuloAndIdPerfilAndEstado(Integer idModulo, Integer idPerfil, Integer estado);

	@Transactional
	void deleteByIdPerfil(Integer idPerfil);

	@Transactional
	void deleteByIdModulo(Integer idModulo);

	// Métodos para consultas dinámicas
	List<Acceso> findByIdPerfilAndEstado(Integer idPerfil, Integer estado);

	List<Acceso> findByIdPerfil(Integer idPerfil);

	// Query para obtener accesos ordenados por módulo
	@Query("SELECT a FROM Acceso a WHERE a.idPerfil = :idPerfil AND a.estado = 1 ORDER BY a.idModulo")
	List<Acceso> findAccesosActivosByPerfil(@Param("idPerfil") Integer idPerfil);
}
