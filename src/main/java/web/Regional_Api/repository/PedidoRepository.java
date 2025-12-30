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

    List<Pedido> findByIdSucursal(Integer idSucursal);

    List<Pedido> findByIdUsuario(Integer idUsuario);

    List<Pedido> findByIdMesa(Integer idMesa);

    List<Pedido> findByIdPlato(Integer idPlato);

    List<Pedido> findByEstado(Integer estado);

    List<Pedido> findByTipoPedido(String tipoPedido);

    @Query("SELECT p FROM Pedido p WHERE p.fechaCreacion BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> buscarPorFecha(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT p FROM Pedido p WHERE p.idSucursal = :idSucursal AND p.estado = :estado")
    List<Pedido> buscarPorSucursalYEstado(
            @Param("idSucursal") Integer idSucursal,
            @Param("estado") Integer estado);

    @Query("SELECT p FROM Pedido p WHERE p.idUsuario = :idUsuario AND p.estado = :estado")
    List<Pedido> buscarPorUsuarioYEstado(
            @Param("idUsuario") Integer idUsuario,
            @Param("estado") Integer estado);

    @Query("SELECT p FROM Pedido p WHERE p.idSucursal = :idSucursal AND p.estado = 1 ORDER BY p.fechaCreacion DESC")
    List<Pedido> buscarPedidosActivosBySucursal(@Param("idSucursal") Integer idSucursal);

    @Query(value = "SELECT * FROM pedidos WHERE id_mesa = :idMesa AND estado = 1 ORDER BY fecha_creacion DESC LIMIT 1", nativeQuery = true)
    Optional<Pedido> findUltimoPedidoActivoByMesa(@Param("idMesa") Integer idMesa);
}
