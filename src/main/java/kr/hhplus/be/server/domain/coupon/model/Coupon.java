package kr.hhplus.be.server.domain.coupon.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public Coupon(Long id, CouponType type, int value, int maxQuantity, int issuedQuantity, CouponStatus status, String code) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.maxQuantity = maxQuantity;
        this.issuedQuantity = issuedQuantity;
        this.status = status;
        this.code = code;
    }


    //쿠폰 발급 생성
    public IssueCoupon issueCoupon(User user) {
        validateActive();
        validateIssuable();

        // 수량 초과 여부 체크
        if (this.issuedQuantity >= this.maxQuantity) {
            throw new BusinessException(ErrorCode.EXCEEDED_QUANTITY_COUPON.getCode(), ErrorCode.EXCEEDED_QUANTITY_COUPON.getMessage());
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

    //쿠폰 활성화 여부 검증
    public void validateActive() {
        if (!CouponStatus.ACTIVE.equals(this.status)) {
            throw new BusinessException(ErrorCode.INVALID_COUPON.getCode(), ErrorCode.INVALID_COUPON.getMessage());
        }
    }

    //발급 가능한지 검증
    public void validateIssuable() {
        if (this.issuedQuantity >= this.maxQuantity) {
            throw new BusinessException(ErrorCode.EXCEEDED_QUANTITY_COUPON.getCode(), ErrorCode.EXCEEDED_QUANTITY_COUPON.getMessage());
        }
    }


}