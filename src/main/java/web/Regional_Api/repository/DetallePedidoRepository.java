package web.Regional_Api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    
    List<DetallePedido> findByIdPedido_Id_pedido(Integer id_pedido);
    
    List<DetallePedido> findByIdPlato_Id_plato(Integer id_plato);
    
    @Query("SELECT dp FROM DetallePedido dp WHERE dp.id_pedido.id_pedido = :id_pedido")
    List<DetallePedido> detallesPorPedido(@Param("id_pedido") Integer id_pedido);
    
    @Query("SELECT SUM(dp.subtotal) FROM DetallePedido dp WHERE dp.id_pedido.id_pedido = :id_pedido")
    BigDecimal calcularTotalPedido(@Param("id_pedido") Integer id_pedido);
}
