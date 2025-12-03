package web.Regional_Api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.SuperAdmin;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Integer> {

    Optional<SuperAdmin> findByEmail(String email);

    Optional<SuperAdmin> findByTokenLogin(String tokenLogin);

    boolean existsByEmail(String email);

    long countByEstado(Integer estado);

    long countByRol(String rol);
}
