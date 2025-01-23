package kr.hhplus.be.server.domain.order.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

}
