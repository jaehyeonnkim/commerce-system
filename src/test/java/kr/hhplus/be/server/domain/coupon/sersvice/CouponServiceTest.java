package kr.hhplus.be.server.domain.coupon.sersvice;

import kr.hhplus.be.server.api.coupon.dto.CouponRequest;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import kr.hhplus.be.server.domain.coupon.model.IssueCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.repository.IssueCouponJpaRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class )
class CouponServiceTest {
    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponJpaRepository couponJpaRepository;

    @Mock
    private IssueCouponJpaRepository issueCouponJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("선착순 쿠폰 발급 성공")
    public void 선착순_쿠폰_발급_성공() {
        // given
        CouponRequest couponRequest = new CouponRequest(1L, 1L);
        User user = new User(1L, "김재현");
        Coupon coupon = new Coupon(1L, CouponType.FIXED, 5000, 100, 10, CouponStatus.ACTIVE, "TEST COUPON");

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(couponJpaRepository.findByIdWithLock(1L)).thenReturn(coupon);
        when(issueCouponJpaRepository.save(any(IssueCoupon.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(couponJpaRepository.save(coupon)).thenReturn(coupon);

        // when
        CouponResponse couponResponse = couponService.issueCoupon(couponRequest);

        // then
        assertNotNull(couponResponse);
        assertEquals(1L, couponResponse.getCouponId());
    }

    @Test
    @DisplayName("사용자 정보 없을 때 IllegalArgumentException 반환")
    public void 선착순_쿠폰_발급_실패_사용자정보_없읍() {
        // given
        CouponRequest couponRequest = new CouponRequest(1L, 1L);
        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> couponService.issueCoupon(couponRequest),
                "사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("쿠폰 정보 없을 때 IllegalArgumentException 반환")
    public void 선착순_쿠폰_발급_실패_쿠폰정보_없음() {
        // given
        CouponRequest couponRequest = new CouponRequest(1L, 1L);
        User user = new User(1L, "김재현");

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(couponJpaRepository.findByIdWithLock(1L)).thenReturn(null);

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> couponService.issueCoupon(couponRequest),
                "쿠폰을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("쿠폰 조회 성공")
    public void 쿠폰_조회_성공() {
        // given
        Long userId = 1L;
        User user = new User(1L, "김재현");
        List<CouponResponse> couponResponses = List.of(new CouponResponse(1L, CouponType.FIXED, 2000, "TEST COUPON"));

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(couponJpaRepository.findAllbyId(1L)).thenReturn(couponResponses);

        // when
        List<CouponResponse> result = couponService.getCouponList(userId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("사용자 정보 없을 때 IllegalArgumentException 반환")
    public void 쿠폰_조회_실패_사용자정보_없음() {
        // given
        Long userId = 1L;
        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> couponService.getCouponList(userId),
                "사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("쿠폰정보 없을 때 빈 리스트 반환 ")
    public void 쿠폰_조회_성공_쿠폰정보_없음() {
        // given
        Long userId = 1L;
        User user = new User(1L, "John Doe");

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(couponJpaRepository.findAllbyId(1L)).thenReturn(List.of()); // 빈 리스트 반환

        // when
        List<CouponResponse> result = couponService.getCouponList(userId);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}