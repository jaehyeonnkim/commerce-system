package kr.hhplus.be.server.api.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.coupon.dto.response.UserCouponResponse;
import kr.hhplus.be.server.domain.coupon.dto.CouponRequest;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.api.coupon.dto.CouponResult;
import kr.hhplus.be.server.api.order.dto.OrderResult;
import kr.hhplus.be.server.application.coupon.dto.response.CouponFacadeResponse;
import kr.hhplus.be.server.application.coupon.facade.CouponFacade;
import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.common.dto.ApiResponse;
import kr.hhplus.be.server.domain.coupon.sersvice.CouponService;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
@Tag(name = "쿠폰 API", description = "쿠폰을 관리하는 API")
public class CouponController {
    private final CouponFacade couponFacade;

    //쿠폰 발급
    @Operation(summary = "쿠폰 발급")
    @PostMapping("/{userId}/issue")
    public ApiResponse<CouponResult> issueCoupon(@RequestBody CouponRequest request) {
        CouponFacadeResponse result = couponFacade.issueCoupon(request.toCouponFacadeRequest());
        return ApiResponse.ok(CouponResult.toResponse(result), LocalDateTime.now());
    }
    //보유 쿠폰 조회
    @Operation(summary = "쿠폰 조회")
    @GetMapping("/{userId}")
    public ApiResponse<UserCouponResponse> getCoupon(@PathVariable Long userId) {
        UserCouponResponse result = couponFacade.getCouponList(userId);

        return ApiResponse.ok(UserCouponResponse.toResponse(result), LocalDateTime.now());
    }
}


