package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.api.coupon.dto.CouponRequest;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final UserJpaRepository userJpaRepository;
    private final CouponJpaRepository couponJpaRepository;

    /**
     * 보유 쿠폰 조회
     */
    public List<CouponResponse> getCouponList(Long userId) {
        return couponJpaRepository.findAllbyId(userId);
    }

    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }


}
