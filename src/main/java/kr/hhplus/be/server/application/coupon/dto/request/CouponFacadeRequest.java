package kr.hhplus.be.server.application.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class CouponFacadeRequest {
    private Long userId;
    private Long couponId;
}
