package kr.hhplus.be.server.application.payment.facade;

import kr.hhplus.be.server.domain.payment.dto.PaymentRequest;
import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.application.payment.dto.request.PaymentFacadeRequest;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.sersvice.CouponService;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.payment.service.PaymentService;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentFacade {
    private final UserService userService;
    private final OrderService orderService;
    private final CouponService couponService;
    private final PaymentService paymentService;

    //결제
    @Transactional
    public PaymentFacadeResponse payOrder(PaymentFacadeRequest request) {
        User user = userService.getUserById(request.getUserId());

        Order order = orderService.getOrderById(request.getOrderId());

        Coupon coupon = couponService.getCouponById(request.getCouponId());
        if (coupon != null) {
            coupon.validateActive();
        }

        PaymentResponse Response = paymentService.payOrder(
                user,
                order,
                coupon,
                new PaymentRequest(
                        user.getId(),
                        order.getId(),
                        coupon.getId(),
                        request.getPaymentType()
                )
        );

        return PaymentFacadeResponse.toResponse(Response);
    }

}
