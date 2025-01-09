package kr.hhplus.be.server.api.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CouponRequest {
    private Long userId;
    private Long couponId;

    public CouponRequest(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }
}
