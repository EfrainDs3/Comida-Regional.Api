package web.Regional_Api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    Optional<Categoria> findByNombre(String nombre);
    
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT c FROM Categoria c WHERE c.estado = 1 ORDER BY c.nombre")
    List<Categoria> categoriasActivas();
}