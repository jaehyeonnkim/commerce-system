package kr.hhplus.be.server.interfaces.coupon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    //쿠폰 발급
    @PostMapping("/{userId}/issue")
    public ResponseEntity<Object> issueCoupon(@PathVariable Long userId,
                                                @RequestBody CouponRequest couponRequest) {

        Map<String, Object> response = Map.of(
                "userId", "12345",
                "couponCode", "2025NEWYEAR",
                "fixed", "type",
                "value", 10000,
                "expirationDate", "2025-12-31",
                "issuedAt", "2025-01-03T12,00,00Z"
        );

        return ResponseEntity.ok(response);
    }
    
    //보유 쿠폰 조회
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getCoupon(@PathVariable Long userId) {
        
        Map<String, Object> response = Map.of(
                "userId", "12345",
                "couponList", List.of(
                        Map.of(
                                "couponId", 123123,
                                "couponCode", "2025NEWYEAR",
                                "type", "fixed",
                                "value", 10000,
                                "expirationDate", "2025-12-31",
                                "issuedAt", "2025-01-03T12,00,00Z",
                                "status", "active"
                        ),
                        Map.of(
                                "couponId", 65211,
                                "couponCode", "THANKYOU",
                                "type", "percent",
                                "value", 20,
                                "expirationDate", "2024-12-31",
                                "issuedAt", "2024-10-30T12,00,00Z",
                                "status", "expired"
                        )
                )
        );
        return ResponseEntity.ok(response);
        
    }
}


