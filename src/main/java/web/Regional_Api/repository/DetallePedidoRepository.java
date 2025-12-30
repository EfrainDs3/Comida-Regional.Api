package web.Regional_Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    @Query("SELECT dp FROM DetallePedido dp WHERE dp.pedido.idPedido = :idPedido ORDER BY dp.idDetalle")
    List<DetallePedido> buscarPorPedido(@Param("idPedido") Integer idPedido);

    @Query("SELECT dp FROM DetallePedido dp WHERE dp.idPlato = :idPlato")
    List<DetallePedido> buscarPorPlato(@Param("idPlato") Integer idPlato);

    @Query("SELECT dp FROM DetallePedido dp WHERE dp.pedido.idPedido = :idPedido AND dp.idPlato = :idPlato")
    List<DetallePedido> findByPedidoAndPlato(
            @Param("idPedido") Integer idPedido,
            @Param("idPlato") Integer idPlato);
}
