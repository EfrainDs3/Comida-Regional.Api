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

    List<Pedido> findByEstadoPedido(String estadoPedido);

    List<Pedido> findByTipoPedido(String tipoPedido);

    @Query("SELECT p FROM Pedido p WHERE p.fechaHora BETWEEN :fechaInicio AND :fechaFin ORDER BY p.fechaHora DESC")
    List<Pedido> buscarPorFecha(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT p FROM Pedido p WHERE p.idSucursal = :idSucursal AND p.estadoPedido = :estadoPedido ORDER BY p.fechaHora DESC")
    List<Pedido> buscarPorSucursalYEstado(
            @Param("idSucursal") Integer idSucursal,
            @Param("estadoPedido") String estadoPedido);

    @Query("SELECT p FROM Pedido p WHERE p.idUsuario = :idUsuario AND p.estadoPedido != 'Cancelado' ORDER BY p.fechaHora DESC")
    List<Pedido> buscarPorUsuarioActivos(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT p FROM Pedido p WHERE p.idSucursal = :idSucursal AND p.estadoPedido != 'Cancelado' ORDER BY p.fechaHora DESC")
    List<Pedido> buscarPedidosActivosBySucursal(@Param("idSucursal") Integer idSucursal);

    @Query(value = "SELECT * FROM pedidos WHERE id_mesa = :idMesa AND estado_pedido != 'Cancelado' ORDER BY fecha_hora DESC LIMIT 1", nativeQuery = true)
    Optional<Pedido> findUltimoPedidoActivoByMesa(@Param("idMesa") Integer idMesa);

    @Query("SELECT p FROM Pedido p WHERE p.nombreCliente LIKE %:nombreCliente%")
    List<Pedido> buscarPorNombreCliente(@Param("nombreCliente") String nombreCliente);

    @Query("SELECT DISTINCT p FROM Pedido p JOIN p.detalles dp WHERE dp.idPlato = :idPlato")
    List<Pedido> buscarPorPlato(@Param("idPlato") Integer idPlato);
}
