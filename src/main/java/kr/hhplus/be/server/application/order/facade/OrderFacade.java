package kr.hhplus.be.server.application.order.facade;

import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.service.ProductService;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    //주문
    @Transactional
    public OrderFacadeResponse orderProducts(OrderFacadeRequest request) {
        User user = userService.getUserById(request.getUserId());

        Product product = productService.findProductById(request.getProductId());

        OrderResponse response = orderService.orderProducts(
                user,
                product,
                new OrderRequest(
                        user.getId(),
                        request.getQuantity(),
                        request.getProductId()
                )
        );

        return OrderFacadeResponse.toResponse(response);
    }

}
