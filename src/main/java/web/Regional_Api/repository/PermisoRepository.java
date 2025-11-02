package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.Regional_Api.entity.Permisos;

public interface PermisoRepository extends JpaRepository<Permisos, Integer> {
  
}
