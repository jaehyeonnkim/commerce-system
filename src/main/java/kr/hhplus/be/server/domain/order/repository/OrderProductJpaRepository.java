package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {

    @Query(value = "SELECT op.product_id, SUM(op.quantity) AS total_quantity " +
            "FROM order_product op " +
            "WHERE op.ordered_at >= DATE_SUB(NOW(), INTERVAL 3 DAY) " +
            "GROUP BY op.product_id " +
            "ORDER BY total_quantity DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findPopularProducts();
}
