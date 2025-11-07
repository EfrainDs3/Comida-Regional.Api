package web.Regional_Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Plato;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    
    List<Plato> findByIdCategoria_Id_categoria(Integer id_categoria);
    
    List<Plato> findByIdSucursal_Id_sucursal(Integer id_sucursal);
    
    List<Plato> findByNombreContainingIgnoreCase(String nombre);
    
    List<Plato> findByDisponible(Integer disponible);
    
    @Query("SELECT p FROM Plato p WHERE p.id_categoria.id_categoria = :id_categoria AND p.disponible = 1 AND p.estado = 1")
    List<Plato> platosDisponiblesPorCategoria(@Param("id_categoria") Integer id_categoria);
    
    @Query("SELECT p FROM Plato p WHERE p.id_sucursal.id_sucursal = :id_sucursal AND p.disponible = 1 AND p.estado = 1")
    List<Plato> platosDisponiblesPorSucursal(@Param("id_sucursal") Integer id_sucursal);
    
    @Query("SELECT p FROM Plato p WHERE p.nombre LIKE %:search% OR p.descripcion LIKE %:search% ORDER BY p.nombre")
    List<Plato> buscarPlatos(@Param("search") String search);
}

