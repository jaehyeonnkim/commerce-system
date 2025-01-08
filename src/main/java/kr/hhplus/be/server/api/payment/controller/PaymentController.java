package kr.hhplus.be.server.api.payment.controller;


import kr.hhplus.be.server.api.payment.dto.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pay")
public class PaymentController {

    //상품 결제
    @PostMapping("/{userId}")
    public ResponseEntity<Object> payProducts(@PathVariable Long userId,
                                                @RequestBody PaymentRequest paymentRequest) {

        Map<String, Object> response = Map.of(
                "paymentId", 12345,
                "orderId", 2149,
                "couponId", 112,
                "status","SUCCESS",
                "amount", 58000,
                "date","2025-01-03T12:34:56Z"
        );

        return ResponseEntity.ok(response);
    }
}
