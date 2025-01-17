package kr.hhplus.be.server.application.coupon.facade;

import kr.hhplus.be.server.application.coupon.dto.request.CouponFacadeRequest;
import kr.hhplus.be.server.application.coupon.dto.response.CouponFacadeResponse;
import kr.hhplus.be.server.application.coupon.dto.response.UserCouponResponse;
import kr.hhplus.be.server.application.payment.dto.request.PaymentFacadeRequest;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.coupon.dto.CouponRequest;
import kr.hhplus.be.server.domain.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.sersvice.CouponService;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.payment.dto.PaymentRequest;
import kr.hhplus.be.server.domain.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.payment.service.PaymentService;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponFacade {
    private final UserService userService;
    private final OrderService orderService;
    private final CouponService couponService;
    private final PaymentService paymentService;

    //선착순 쿠폰 발급
    @Transactional
    public CouponFacadeResponse issueCoupon(CouponFacadeRequest request) {
        User user = userService.getUserById(request.getUserId());

        Coupon coupon = couponService.getCouponById(request.getCouponId());

        CouponResponse Response = couponService.issueCoupon(
                user,
                coupon,
                new CouponRequest(
                        user.getId(),
                        coupon.getId()
                )
        );

        return CouponFacadeResponse.toResponse(Response);
    }

    @Transactional
    public UserCouponResponse getCouponList(Long userId) {
        User user = userService.getUserById(userId);

        List<CouponResponse> issuedCoupons = couponService.getCouponList(userId);

        return UserCouponResponse.builder()
                .issuedCouponList(issuedCoupons)
                .build();

    }
}
