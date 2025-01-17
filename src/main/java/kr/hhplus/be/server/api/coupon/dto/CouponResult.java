package kr.hhplus.be.server.api.coupon.dto;

import kr.hhplus.be.server.application.coupon.dto.response.CouponFacadeResponse;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CouponResult {
    private Long couponId;
    private String name;
    private String code;
    private CouponType type;
    private int value;
    private Boolean isUsed;
    private LocalDateTime issuedAt;
    private LocalDateTime usedAt;
    private LocalDateTime expiredAt;

    public static CouponResult toResponse(CouponFacadeResponse response) {
        return CouponResult.builder()
                .couponId(response.getCouponId())
                .name(response.getName())
                .code(response.getCode())
                .type(response.getType())
                .value(response.getValue())
                .isUsed(response.getIsUsed())
                .issuedAt(response.getIssuedAt())
                .usedAt(response.getUsedAt())
                .expiredAt(response.getExpiredAt())
                .build();
    }
}
