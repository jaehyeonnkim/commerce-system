package kr.hhplus.be.server.api.coupon.dto;

import kr.hhplus.be.server.domain.coupon.CouponType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

}
