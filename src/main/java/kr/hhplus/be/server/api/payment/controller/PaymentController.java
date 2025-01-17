package kr.hhplus.be.server.api.payment.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.order.dto.OrderResult;
import kr.hhplus.be.server.domain.payment.dto.PaymentRequest;
import kr.hhplus.be.server.api.payment.dto.PaymentResult;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.application.payment.facade.PaymentFacade;
import kr.hhplus.be.server.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
@Tag(name = "결제 API", description = "결제를 관리하는 API")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    //상품 결제
    @Operation(summary = "상품 결제")
    @PostMapping("/{userId}")
    public ApiResponse<PaymentResult> payProducts(@RequestBody PaymentRequest request) {
        PaymentFacadeResponse result = paymentFacade.payOrder(request.toPaymentFacadeRequest());
        return ApiResponse.ok(PaymentResult.toResponse(result), LocalDateTime.now());
    }
}
