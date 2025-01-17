package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.api.order.dto.OrderRequest;
import kr.hhplus.be.server.api.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.order.model.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderJpaRepository;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ProductRepository productRepository;

    /**
     * 주문
     */
    public OrderResponse order(OrderRequest orderRequest) {
        OrderResponse orderResponse = new OrderResponse();
        //엔티티 조회
        User user = findByIdOrThrow(userJpaRepository, orderRequest.getUserId(), "사용자를 찾을 수 없습니다.");
        Product product = productRepository.findById(orderRequest.getProductId());

        //주문 상품
        OrderProduct orderProduct = OrderProduct.createOrderItem(product, product.getPrice(), orderRequest.getQuantity());

        //주문 생성
        Order order = Order.createOrder(user, orderProduct);
        orderJpaRepository.save(order);

        orderResponse.setId(order.getId());
        orderResponse.setTotalAmount(order.getTotalAmount());
        return orderResponse;
    }

    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }


}
