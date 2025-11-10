package web.Regional_Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.estado_pedido = :estado")
    List<Pedido> findByEstadoPedido(@Param("estado") String estado);

    @Query("SELECT p FROM Pedido p WHERE p.sucursal.idSucursal = :idSucursal")
    List<Pedido> findBySucursalId(@Param("idSucursal") Integer idSucursal);

}
