package kr.hhplus.be.server.domain.coupon.sersvice;

import kr.hhplus.be.server.api.coupon.dto.CouponRequest;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.repository.IssueCouponJpaRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CouponServiceConcurrencyTest {
    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponJpaRepository couponJpaRepository;

    @Mock
    private IssueCouponJpaRepository issueCouponJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    private Coupon coupon;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User(1L, "김재현");
        coupon = new Coupon(1L, CouponType.FIXED, 5000, 10, 0, CouponStatus.ACTIVE, "TEST COUPON");

        when(userJpaRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(couponJpaRepository.findByIdWithLock(anyLong())).thenReturn(coupon);
        when(couponJpaRepository.save(any(Coupon.class))).thenReturn(coupon);
        when(issueCouponJpaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("동시성 테스트 - 쿠폰 선착순 발급 성공")
    public void testIssueCoupon_ConcurrencySuccess() throws InterruptedException {
        // given
        int numberOfThreads = 10; // 쓰레드 수
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(() -> {
                try {
                    CouponRequest couponRequest = new CouponRequest(user.getId(), coupon.getId());
                    couponService.issueCoupon(couponRequest);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        assertEquals(10, coupon.getIssuedQuantity()); //발급수량 확인
        assertTrue(coupon.getIssuedQuantity() <= coupon.getMaxQuantity()); // 발급 수량이 최대 수량을 초과하지 않는지 확인
    }
}
