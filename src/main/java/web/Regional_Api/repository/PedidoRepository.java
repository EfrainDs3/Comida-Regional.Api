package web.Regional_Api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findBySucursal_IdSucursal(Integer idSucursal);

    List<Pedido> findByUsuario_IdUsuario(Integer idUsuario);

    List<Pedido> findByEstado(String estado);

    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin AND p.sucursal.idSucursal = :idSucursal")
    List<Pedido> findPedidosByFechaRangeAndSucursal(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("idSucursal") Integer idSucursal);

    @Query("SELECT p FROM Pedido p WHERE p.sucursal.idSucursal = :idSucursal AND p.estado = :estado")
    List<Pedido> findBySucursal_IdSucursalAndEstado(
            @Param("idSucursal") Integer idSucursal,
            @Param("estado") String estado);

    @Query("SELECT p FROM Pedido p WHERE p.usuario.idUsuario = :idUsuario AND p.estado = :estado")
    List<Pedido> findByIdUsuarioAndEstado(
            @Param("idUsuario") Integer idUsuario,
            @Param("estado") String estado);

    List<Pedido> findByTipoPedido(String tipoPedido);

    @Query(value = "SELECT * FROM pedidos WHERE id_mesa = :idMesa ORDER BY fecha_hora DESC LIMIT 1", nativeQuery = true)
    Optional<Pedido> findUltimoPedidoByMesa(@Param("idMesa") Integer idMesa);

    @Query("SELECT p FROM Pedido p WHERE p.sucursal.idSucursal = :idSucursal AND p.estado != 'cancelado' ORDER BY p.fechaPedido DESC")
    List<Pedido> findPedidosActivosBySucursal(@Param("idSucursal") Integer idSucursal);

    @Query("SELECT p FROM Pedido p WHERE p.sucursal.idSucursal = :idSucursal")
    List<Pedido> buscarPorSucursal(@Param("idSucursal") Integer idSucursal);

    @Query("SELECT p FROM Pedido p WHERE p.estado = :estado")
    List<Pedido> buscarPorEstado(@Param("estado") String estado);

    @Query("SELECT p FROM Pedido p WHERE p.usuario.idUsuario = :idUsuario")
    List<Pedido> buscarPorUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT p FROM Pedido p WHERE p.mesa.id_mesa = :idMesa")
    List<Pedido> buscarPorMesa(@Param("idMesa") Integer idMesa);

    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> buscarPorFecha(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT p FROM Pedido p WHERE p.sucursal.idSucursal = :idSucursal AND p.estado = :estado")
    List<Pedido> buscarPorSucursalYEstado(
            @Param("idSucursal") Integer idSucursal,
            @Param("estado") String estado);
}
