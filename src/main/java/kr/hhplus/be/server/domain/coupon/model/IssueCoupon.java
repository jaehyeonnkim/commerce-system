package kr.hhplus.be.server.domain.coupon.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class IssueCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name="is_used")
    private boolean used;

    private LocalDateTime usedAt;
    private LocalDateTime issuedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // IssueCoupon -> CouponResponse 변환 메서드
    public CouponResponse toResponse() {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getType(),
                coupon.getValue(),
                used,
                issuedAt,
                usedAt,
                coupon.getExpiredAt()
        );
    }

}