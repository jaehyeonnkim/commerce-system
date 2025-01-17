package kr.hhplus.be.server.domain.coupon.sersvice;

import kr.hhplus.be.server.application.coupon.dto.response.UserCouponResponse;
import kr.hhplus.be.server.domain.coupon.dto.CouponRequest;
import kr.hhplus.be.server.domain.coupon.dto.CouponResponse;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.coupon.repository.IssueCouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.IssueCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponJpaRepository couponJpaRepository;
    private final IssueCouponJpaRepository issueCouponJpaRepository;


    public List<CouponResponse> getCouponList(Long userId) {
        return couponJpaRepository.findAllbyId(userId);
    }




    //선착순 쿠폰 발급
    public CouponResponse issueCoupon(User user, Coupon coupon, CouponRequest couponRequest) {
        IssueCoupon issueCoupon = coupon.issueCoupon(user);

        couponJpaRepository.findByIdWithLock(issueCoupon.getId());
        issueCouponJpaRepository.save(issueCoupon);

        couponJpaRepository.save(coupon);

        return new CouponResponse(coupon.getId(),coupon.getName(),coupon.getCode(),coupon.getType(),coupon.getValue(), issueCoupon.isUsed(), issueCoupon.getIssuedAt(), issueCoupon.getUsedAt(),coupon.getExpiredAt());
    }

    public Coupon getCouponById(long couponId) {
        return couponJpaRepository.findById(couponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COUPON_NOT_FOUND.getCode(), ErrorCode.COUPON_NOT_FOUND.getMessage()));
    }


}
