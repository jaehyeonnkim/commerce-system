package kr.hhplus.be.server.api.coupon.dto;

import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@AllArgsConstructor
public class CouponResponse {
    private Long couponId;
    private String name;
    private String code;
    private CouponType type;
    private int value;
    private Boolean isUsed;
    private LocalDateTime issuedAt;
    private LocalDateTime usedAt;
    private LocalDateTime expiredAt;

    public CouponResponse(Long id, CouponType type, int value, String code) {
        this.couponId = id;
        this.type = type;
        this.value = value;
        this.code = code;
    }

}
