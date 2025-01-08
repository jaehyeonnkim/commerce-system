package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.api.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.infra.OrderCoreWriterRepository;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.order.model.OrderProduct;
import kr.hhplus.be.server.domain.product.infra.ProductCoreReaderRepository;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductWriterRepository;
import kr.hhplus.be.server.domain.user.infra.UserCoreReaderRepository;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderWriter {

    private final OrderCoreWriterRepository orderRepository;
    private final UserCoreReaderRepository userCoreReaderRepository;
    private final ProductCoreReaderRepository productCoreReaderRepository;

    /**
     * 주문
     */
    @Transactional
    public OrderResponse order(Long userId, Long productId, int quantity){
        OrderResponse orderResponse = new OrderResponse();
        //엔티티 조회
        User user = userCoreReaderRepository.findById(userId);
        Product product = productCoreReaderRepository.findById(productId);

        //주문 상품
        OrderProduct orderProduct = OrderProduct.createOrderItem(product, product.getPrice(), quantity);

        //주문 생성
        Order order = Order.createOrder(user, orderProduct);
        orderRepository.save(order);

        orderResponse.setId(order.getId());
        return orderResponse;
    }


}
