package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.api.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.user.UserRepository;
import kr.hhplus.be.server.infra.order.OrderRepositoryImpl;
import kr.hhplus.be.server.infra.product.ProductRepositoryImpl;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.infra.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * 주문
     */
    @Transactional
    public OrderResponse order(Long userId, Long productId, int quantity){
        OrderResponse orderResponse = new OrderResponse();
        //엔티티 조회
        User user = userRepository.findById(userId);
        Product product = productRepository.findById(productId);

        //주문 상품
        OrderProduct orderProduct = OrderProduct.createOrderItem(product, product.getPrice(), quantity);

        //주문 생성
        Order order = Order.createOrder(user, orderProduct);
        orderRepository.save(order);

        orderResponse.setId(order.getId());
        return orderResponse;
    }


}
