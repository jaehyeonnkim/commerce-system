package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderWriterRepository {

    void save(Order order);

}
