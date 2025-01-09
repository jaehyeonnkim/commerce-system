package kr.hhplus.be.server.api.payment.dto;

import kr.hhplus.be.server.domain.payment.model.PaymentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentRequest {
    private Long userId;
    private Long orderId;
    private Long couponId;
    private PaymentType paymentType;

    public PaymentRequest(Long userId, Long orderId, Long couponId, PaymentType paymentType) {
        this.userId = userId;
        this.orderId = orderId;
        this.couponId = couponId;
        this.paymentType = paymentType;
    }
}
