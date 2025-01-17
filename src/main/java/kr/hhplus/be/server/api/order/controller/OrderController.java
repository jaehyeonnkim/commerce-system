package kr.hhplus.be.server.api.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.order.dto.OrderResult;
import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.application.order.facade.OrderFacade;
import kr.hhplus.be.server.common.dto.ApiResponse;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "주문 API", description = "주문을 관리하는 API")
public class OrderController {

    private final OrderFacade orderFacade;


    //상품 주문
    @Operation(summary = "상품 주문")
    @PostMapping("/{userId}")
    public ApiResponse<OrderResult> orderProducts(@RequestBody OrderRequest request) {
        OrderFacadeResponse result = orderFacade.orderProducts(request.toOrderFacadeRequest());
        return ApiResponse.ok(OrderResult.toResponse(result), LocalDateTime.now());
    }
}

