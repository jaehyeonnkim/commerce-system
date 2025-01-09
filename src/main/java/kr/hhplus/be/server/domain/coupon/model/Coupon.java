package kr.hhplus.be.server.domain.coupon.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Comment("쿠폰 이름")
    private String name;

    @Comment("쿠폰 코드")
    private String code;

    @Enumerated(EnumType.STRING)
    @Comment("쿠폰 타입")
    private CouponType type;

    @Comment("할인 값")
    private int value;

    @Enumerated(EnumType.STRING)
    @Comment("쿠폰 상태")
    private CouponStatus status;

    @Comment("쿠폰 갯수")
    private int maxQuantity;
    @Comment("쿠폰 발급 갯수")
    private int issuedQuantity;

    @Comment("쿠폰 유효기간 시작일")
    private LocalDateTime startedAt;
    @Comment("쿠폰 유효기간 만료인")
    private LocalDateTime expiredAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //쿠폰 발급 생성
    public IssueCoupon issueCoupon(User user) {
        // 수량 초과 여부 체크
        if (this.issuedQuantity >= this.maxQuantity) {
            throw new IllegalStateException("쿠폰 발급 수량이 초과되었습니다.");
        }

        // 발급 수량 증가
        this.issuedQuantity++;

        // 발급 기록 생성
        IssueCoupon issueCoupon = new IssueCoupon();
        issueCoupon.setCoupon(this);
        issueCoupon.setUser(user);
        issueCoupon.setIssuedAt(LocalDateTime.now());

        return issueCoupon;
    }
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