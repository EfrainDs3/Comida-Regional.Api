package web.Regional_Api.repository;
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

<<<<<<< Updated upstream

    // Los métodos usan consultas explícitas para evitar ambigüedades en rutas de propiedades.
    @Query("SELECT dp FROM DetallePedido dp WHERE dp.id_pedido.id_pedido = :id_pedido")
    List<DetallePedido> detallesPorPedido(@Param("id_pedido") Integer id_pedido);

    @Query("SELECT dp FROM DetallePedido dp WHERE dp.id_plato.id_plato = :id_plato")
    List<DetallePedido> detallesPorPlato(@Param("id_plato") Integer id_plato);

    @Query("SELECT SUM(dp.subtotal) FROM DetallePedido dp WHERE dp.id_pedido.id_pedido = :id_pedido")
    BigDecimal calcularTotalPedido(@Param("id_pedido") Integer id_pedido);
}

}

=======
}
>>>>>>> Stashed changes
