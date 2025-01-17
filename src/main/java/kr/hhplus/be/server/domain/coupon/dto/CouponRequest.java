package kr.hhplus.be.server.domain.coupon.dto;

import kr.hhplus.be.server.application.coupon.dto.request.CouponFacadeRequest;
import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
public class CouponRequest {
    private Long userId;
    private Long couponId;

    public CouponFacadeRequest toCouponFacadeRequest(){
        return CouponFacadeRequest.builder()
                .couponId(couponId)
                .userId(userId)
                .build();
    }
}