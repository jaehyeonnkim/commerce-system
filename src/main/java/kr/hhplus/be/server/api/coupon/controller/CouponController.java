package kr.hhplus.be.server.api.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.coupon.dto.CouponRequest;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.sersvice.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
@Tag(name = "쿠폰 API", description = "쿠폰을 관리하는 API")
public class CouponController {
    private final CouponService couponService;

    //쿠폰 발급
    @Operation(summary = "쿠폰 발급")
    @PostMapping("/{userId}/issue")
    public CouponResponse issueCoupon(@RequestBody CouponRequest couponRequest) {
        return couponService.issueCoupon(couponRequest);
    }
    //보유 쿠폰 조회
    @Operation(summary = "쿠폰 조회")
    @GetMapping("/{userId}")
    public List<CouponResponse> getCoupon(@PathVariable Long userId) {
        return couponService.getCouponList(userId);
    }
}


