package kr.hhplus.be.server.domain.coupon.sersvice;

import kr.hhplus.be.server.api.coupon.dto.CouponRequest;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.repository.IssueCouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.IssueCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponJpaRepository couponJpaRepository;
    private final IssueCouponJpaRepository issueCouponJpaRepository;
    private final UserJpaRepository userJpaRepository;

    /**
     * 보유 쿠폰 조회
     */
    public List<CouponResponse> getCouponList(Long userId) {
        User user = findByIdOrThrow(userJpaRepository, userId, "사용자를 찾을 수 없습니다.");

        return couponJpaRepository.findAllbyId(userId);
    }



    /**
     * 선착순 쿠폰 발급
     */
    public CouponResponse issueCoupon(CouponRequest couponRequest) {
        User user = findByIdOrThrow(userJpaRepository, couponRequest.getUserId(), "사용자를 찾을 수 없습니다.");

        Coupon coupon = couponJpaRepository.findByIdWithLock(couponRequest.getCouponId());
        if (coupon == null) {
            throw new IllegalArgumentException("쿠폰을 찾을 수 없습니다.");
        }

        coupon = couponJpaRepository.findByIdWithLock(couponRequest.getCouponId());

        // 쿠폰 발급 및 발급 기록 생성
        IssueCoupon issueCoupon = coupon.issueCoupon(user);

        // 발급 기록 저장
        issueCouponJpaRepository.save(issueCoupon);

        // 쿠폰 수량 저장
        couponJpaRepository.save(coupon);

        CouponResponse couponResponse = new CouponResponse();
        couponResponse.setCouponId(couponRequest.getCouponId());
        return couponResponse;
    }


    /**
     * 공통 메서드
     */
    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }
}
