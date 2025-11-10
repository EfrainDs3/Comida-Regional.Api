package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
<<<<<<< HEAD
    
    @Query("SELECT p FROM Pedido p WHERE p.numero_pedido = :numero_pedido")
    Optional<Pedido> buscarPorNumeroPedido(@Param("numero_pedido") String numero_pedido);

    @Query("SELECT p FROM Pedido p WHERE p.id_sucursal.id_sucursal = :id_sucursal")
    List<Pedido> buscarPorSucursal(@Param("id_sucursal") Integer id_sucursal);

    @Query("SELECT p FROM Pedido p WHERE p.estado_pedido = :estado_pedido")
    List<Pedido> buscarPorEstado(@Param("estado_pedido") String estado_pedido);    @Query("SELECT p FROM Pedido p WHERE p.id_sucursal.id_sucursal = :id_sucursal AND p.estado_pedido = :estado_pedido")
    List<Pedido> pedidosPorSucursalYEstado(@Param("id_sucursal") Integer id_sucursal, @Param("estado_pedido") String estado_pedido);
    
    @Query("SELECT p FROM Pedido p WHERE p.fecha_pedido BETWEEN :fechaInicio AND :fechaFin ORDER BY p.fecha_pedido DESC")
    List<Pedido> pedidosPorRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT p FROM Pedido p WHERE p.id_sucursal.id_sucursal = :id_sucursal AND p.fecha_pedido BETWEEN :fechaInicio AND :fechaFin ORDER BY p.fecha_pedido DESC")
    List<Pedido> pedidosPorSucursalYFechas(@Param("id_sucursal") Integer id_sucursal, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}

=======
    // (Opcional) Métodos de búsqueda:
    // List<Pedido> findByEstadoPedido(String estado);
    // List<Pedido> findByIdSucursal(Integer idSucursal);
}
>>>>>>> 5ba32ea10d85de05f149f524abc14c8c287c4435
