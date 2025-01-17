package kr.hhplus.be.server.api.payment.dto;

import kr.hhplus.be.server.domain.payment.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PaymentRequest {
    private Long userId;
    private Long orderId;
    private Long couponId;
    private PaymentType paymentType;

}
