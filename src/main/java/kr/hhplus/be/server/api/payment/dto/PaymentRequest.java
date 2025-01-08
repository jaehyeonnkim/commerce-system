package kr.hhplus.be.server.api.payment.dto;

import kr.hhplus.be.server.domain.payment.PaymentType;
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
}
