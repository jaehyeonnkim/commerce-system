package kr.hhplus.be.server.api.payment.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.payment.dto.PaymentRequest;
import kr.hhplus.be.server.api.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
@Tag(name = "결제 API", description = "결제를 관리하는 API")
public class PaymentController {

    private final PaymentService paymentService;

    //상품 결제
    @Operation(summary = "상품 결제")
    @PostMapping("/{userId}")
    public PaymentResponse payProducts(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.payOrder(paymentRequest);
    }

}
