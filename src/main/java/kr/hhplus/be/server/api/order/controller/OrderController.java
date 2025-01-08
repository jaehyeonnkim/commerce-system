package kr.hhplus.be.server.api.order.controller;

import kr.hhplus.be.server.api.order.dto.OrderRequest;
import kr.hhplus.be.server.api.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //상품 주문
    @PostMapping("/{userId}")
    public OrderResponse orderProducts(@PathVariable Long userId,
                                       @RequestBody OrderRequest orderRequest) {
       return orderService.order(userId, orderRequest.getProductId(), orderRequest.getQuantity());
    }
}
