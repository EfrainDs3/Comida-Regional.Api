package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
<<<<<<< HEAD
    
    // Los métodos derivados con guiones bajos causaban problemas al parsear la ruta de la propiedad
    // (Spring Data interpreta '_' como separador y terminó buscando una propiedad 'id' que no existe).
    // En su lugar, usamos las consultas explícitas abajo (detallesPorPedido, calcularTotalPedido).
    
    @Query("SELECT dp FROM DetallePedido dp WHERE dp.id_pedido.id_pedido = :id_pedido")
    List<DetallePedido> detallesPorPedido(@Param("id_pedido") Integer id_pedido);
    
    @Query("SELECT dp FROM DetallePedido dp WHERE dp.id_plato.id_plato = :id_plato")
    List<DetallePedido> detallesPorPlato(@Param("id_plato") Integer id_plato);
    
    @Query("SELECT SUM(dp.subtotal) FROM DetallePedido dp WHERE dp.id_pedido.id_pedido = :id_pedido")
    BigDecimal calcularTotalPedido(@Param("id_pedido") Integer id_pedido);
}
=======
}
>>>>>>> 5ba32ea10d85de05f149f524abc14c8c287c4435
