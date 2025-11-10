package web.Regional_Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.Regional_Api.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // (Opcional) Métodos de búsqueda:
    // List<Pedido> findByEstadoPedido(String estado);
    // List<Pedido> findByIdSucursal(Integer idSucursal);
}