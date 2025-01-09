package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.order.OrderProduct;
import kr.hhplus.be.server.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String code;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    private int value;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //validation
    public void validateCoupon() {
        if (this == null) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }
        // 쿠폰 상태 검증
        if (!this.status.equals(CouponStatus.ACTIVE)) {
            throw new IllegalStateException("유효하지 않는 쿠폰입니다.");
        }
    }
}