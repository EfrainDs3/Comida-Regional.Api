package web.Regional_Api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    
    List<Sucursal> findByIdRestaurante_Id_restaurante(Integer id_restaurante);
    
    Optional<Sucursal> findByNombreAndIdRestaurante_Id_restaurante(String nombre, Integer id_restaurante);
    
    List<Sucursal> findByEstado_sucursal(String estado_sucursal);
    
    List<Sucursal> findByCiudad(String ciudad);
    
    @Query("SELECT s FROM Sucursal s WHERE s.id_restaurante.id_restaurante = :id_restaurante AND s.estado_sucursal = 'Activo'")
    List<Sucursal> sucursalesActivasPorRestaurante(@Param("id_restaurante") Integer id_restaurante);
}

