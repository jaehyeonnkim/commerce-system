package kr.hhplus.be.server.api.coupon.dto;

import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
public class CouponRequest {
    private Long userId;
    private Long couponId;

}
