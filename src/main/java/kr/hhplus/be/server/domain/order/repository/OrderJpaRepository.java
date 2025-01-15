package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
