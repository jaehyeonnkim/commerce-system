package kr.hhplus.be.server.domain.payment.service;

import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.payment.dto.PaymentRequest;
import kr.hhplus.be.server.domain.payment.dto.PaymentResponse;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.order.repository.OrderJpaRepository;
import kr.hhplus.be.server.domain.payment.model.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentJpaRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentJpaRepository paymentJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CouponJpaRepository couponJpaRepository;

    //결제
    @Transactional
    public PaymentResponse payOrder(User user, Order order, Coupon coupon, PaymentRequest paymentRequest) {
        Payment payment = Payment.createPayment(user, order, coupon, paymentRequest.getPaymentType());
        paymentJpaRepository.save(payment);
        payment.markAsPaid();
        order.markAsPaid();
        paymentJpaRepository.save(payment);

        return new PaymentResponse(payment.getStatus(), payment.getTotalAmount());
    }

}
