package web.Regional_Api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Sucursales;

@Repository
public interface SucursalesRepository extends JpaRepository<Sucursales, Integer> {
    
    /**
     * Seguridad Multi-Tenant:
     * Encuentra todas las sucursales pertenecientes a un ID de restaurante específico.
     * El filtro por `estado=1` (Soft Delete) lo maneja automáticamente la entidad @Filter.
     */
    List<Sucursales> findByIdRestaurante(Integer idRestaurante);
    
    /**
     * Seguridad Multi-Tenant:
     * Encuentra una sucursal por su ID, asegurando que el ID del restaurante también coincida.
     * Esto previene que un restaurante acceda a la sucursal de otro.
     */
    Optional<Sucursales> findByIdSucursalAndIdRestaurante(Integer idSucursal, Integer idRestaurante);
    
    // El método save(Sucursales), findById(Integer), y deleteById(Integer)
    // se heredan automáticamente de JpaRepository.
}
