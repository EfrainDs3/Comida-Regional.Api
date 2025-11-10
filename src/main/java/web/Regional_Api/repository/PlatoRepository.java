package web.Regional_Api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Plato;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    
    @Query("SELECT p FROM Plato p WHERE p.categoria.id_categoria = :id_categoria")
    List<Plato> buscarPorCategoria(@Param("id_categoria") Integer id_categoria);
    
    @Query("SELECT p FROM Plato p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Plato> buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Plato p WHERE p.precio <= :precio_maximo")
    List<Plato> buscarPorPrecioMenorIgual(@Param("precio_maximo") Double precio_maximo);
}