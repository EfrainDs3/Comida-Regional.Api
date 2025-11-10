package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Insumos;

@Repository
public interface InsumosRepository extends JpaRepository<Insumos, Integer> {
    // Puedes agregar métodos de búsqueda personalizados si los necesitas
    // ej: List<Insumos> findByNombreContaining(String nombre);
}
