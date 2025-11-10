package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.Regional_Api.entity.MovimientosInventario;

@Repository
public interface MovimientosInventarioRepository extends JpaRepository<MovimientosInventario, Integer> {
}
