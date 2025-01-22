package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.application.product.dto.response.TopProductResponse;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.order.model.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderJpaRepository;
import kr.hhplus.be.server.domain.order.repository.OrderProductJpaRepository;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderProductJpaRepository orderProductJpaRepository;

    //주문
    public OrderResponse orderProducts(User user, Product product, OrderRequest orderRequest) {
        OrderProduct orderProduct = OrderProduct.createOrderItem(product, product.getPrice(), orderRequest.getQuantity());

        Order order = Order.createOrder(user, orderProduct);

        orderJpaRepository.save(order);

        return new OrderResponse(order.getId(), order.getTotalAmount());
    }


    public Order getOrderById(long orderId) {
        return orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND.getCode(), ErrorCode.ORDER_NOT_FOUND.getMessage()));
    }

    //인기 상품 조회
    public List<OrderProduct> getPopularProducts() {
        return orderProductJpaRepository.findPopularProducts();
    }
}

