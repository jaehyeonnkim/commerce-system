package kr.hhplus.be.server.api.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.order.dto.OrderRequest;
import kr.hhplus.be.server.api.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "주문 API", description = "주문을 관리하는 API")
public class OrderController {

    private final OrderService orderService;


    //상품 주문
    @Operation(summary = "상품 주문")
    @PostMapping("/{userId}")
    public OrderResponse orderProducts(@RequestBody OrderRequest orderRequest) {
       return orderService.order(orderRequest);
    }
}
