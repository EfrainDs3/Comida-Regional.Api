package web.Regional_Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Plato;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {

    // Buscar todos los platos de una categoría
    @Query("SELECT p FROM Plato p WHERE p.categoria.id_categoria = :id_categoria")
    List<Plato> buscarPorCategoria(@Param("id_categoria") Integer id_categoria);

    // Buscar solo platos disponibles de una categoría (estado = 1)
    @Query("SELECT p FROM Plato p WHERE p.categoria.id_categoria = :id_categoria AND p.estado = 1")
    List<Plato> platosDisponiblesPorCategoria(@Param("id_categoria") Integer id_categoria);

    // Buscar platos por nombre (insensible a mayúsculas/minúsculas)
    @Query("SELECT p FROM Plato p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Plato> buscarPorNombre(@Param("nombre") String nombre);

    // Buscar platos por precio máximo
    @Query("SELECT p FROM Plato p WHERE p.precio <= :precio_maximo")
    List<Plato> buscarPorPrecioMenorIgual(@Param("precio_maximo") Double precio_maximo);

    // Buscar platos por disponibilidad usando 'estado' (1 = disponible, 0 = no disponible)
    List<Plato> findByEstado(Integer estado);

    // Métodos de sucursales (queda intacto)
    List<Plato> findBySucursales_Id_sucursal(Integer id_sucursal);

    @Query("SELECT p FROM Plato p WHERE p.sucursales.id_sucursal = :id_sucursal AND p.estado = 1")
    List<Plato> platosDisponiblesPorSucursales(@Param("id_sucursal") Integer id_sucursal);
}
