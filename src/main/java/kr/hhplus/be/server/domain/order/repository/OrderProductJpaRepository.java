package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {

    @Query(value = "SELECT op.order_product_id, op.product_id, op.created_at, op.order_id, op.order_price, op.ordered_at, op.updated_at,SUM(op.quantity) AS quantity " +
            "FROM order_product op " +
            "WHERE op.ordered_at >= DATE_SUB(NOW(), INTERVAL 3 DAY) " +
            "GROUP BY op.order_product_id, op.product_id, op.created_at, op.order_id, op.order_price, op.ordered_at, op.updated_at " +
            "ORDER BY quantity DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<OrderProduct> findPopularProducts();
}
