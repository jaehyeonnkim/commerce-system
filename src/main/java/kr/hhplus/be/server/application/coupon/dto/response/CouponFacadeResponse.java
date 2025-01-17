package kr.hhplus.be.server.application.coupon.dto.response;

import kr.hhplus.be.server.domain.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
public class CouponFacadeResponse {
    private Long couponId;
    private String name;
    private String code;
    private CouponType type;
    private int value;
    private Boolean isUsed;
    private LocalDateTime issuedAt;
    private LocalDateTime usedAt;
    private LocalDateTime expiredAt;

    public static CouponFacadeResponse toResponse(CouponResponse response) {
        return CouponFacadeResponse.builder()
                .couponId(response.getCouponId())
                .name(response.getName())
                .code(response.getCode())
                .type(response.getType())
                .value(response.getValue())
                .isUsed(response.getIsUsed())
                .issuedAt(response.getIssuedAt())
                .usedAt(response.getUsedAt())
                .expiredAt(response.getExpiredAt())
                .code(response.getCode())
                .build();
    }
}
