package kr.hhplus.be.server.api.order.controller;

import kr.hhplus.be.server.api.order.dto.OrderRequest;
import kr.hhplus.be.server.api.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.service.OrderWriter;
import kr.hhplus.be.server.domain.product.service.ProductReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderWriter orderWriter;

    public OrderController(OrderWriter orderWriter) {
        this.orderWriter = orderWriter;
    }

    //상품 주문
    @PostMapping("/{userId}")
    public OrderResponse orderProducts(@PathVariable Long userId,
                                       @RequestBody OrderRequest orderRequest) {
       return orderWriter.order(userId, orderRequest.getProductId(), orderRequest.getQuantity());
    }
}
