package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.Regional_Api.entity.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
   
}
