package web.Regional_Api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {
	Optional<Modulo> findByNombreModulo(String nombreModulo);
}
