package kr.hhplus.be.server.api.coupon.controller;

import kr.hhplus.be.server.api.coupon.dto.CouponRequest;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.api.payment.dto.PaymentRequest;
import kr.hhplus.be.server.api.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.coupon.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    //쿠폰 발급
    @PostMapping("/{userId}/issue")
    public ResponseEntity<Object> issueCoupon(@RequestBody CouponRequest couponRequest) {

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
    public List<CouponResponse> getCoupon(@PathVariable Long userId) {
        return couponService.getCouponList(userId);
    }
}


