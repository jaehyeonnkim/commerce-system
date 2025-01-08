package kr.hhplus.be.server.domain.order.infra;

import jakarta.persistence.EntityManager;
import kr.hhplus.be.server.domain.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderCoreWriterRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }
}
