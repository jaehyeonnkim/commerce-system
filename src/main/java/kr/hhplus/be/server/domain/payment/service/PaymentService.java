package kr.hhplus.be.server.domain.payment.service;

import kr.hhplus.be.server.api.payment.dto.PaymentRequest;
import kr.hhplus.be.server.api.payment.dto.PaymentResponse;
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

    /**
     * 결제
     */
    @Transactional
    public PaymentResponse payOrder(PaymentRequest paymentRequest) {
        // 엔티티 조회
        User user = findByIdOrThrow(userJpaRepository, paymentRequest.getUserId(), "사용자를 찾을 수 없습니다.");
        Order order = findByIdOrThrow(orderJpaRepository, paymentRequest.getOrderId(), "주문을 찾을 수 없습니다.");
        Coupon coupon = null;
        if (paymentRequest.getCouponId() != null) {
            coupon = findByIdOrThrow(couponJpaRepository, paymentRequest.getCouponId(), "쿠폰을 찾을 수 없습니다.");
        }
        // 2. 결제 생성
        Payment payment = Payment.createPayment(user, order, coupon, paymentRequest.getPaymentType());
        paymentJpaRepository.save(payment);
        payment.markAsPaid();
        // 3. 주문 상태 변경 ORDER -> PAID
        order.markAsPaid();
        paymentJpaRepository.save(payment);

        // 4. 응답 생성
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(payment.getId());
        paymentResponse.setTotalAmount(payment.getTotalAmount());
        paymentResponse.setStatus(order.getPayment().getStatus());
        return paymentResponse;
    }

    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

}
