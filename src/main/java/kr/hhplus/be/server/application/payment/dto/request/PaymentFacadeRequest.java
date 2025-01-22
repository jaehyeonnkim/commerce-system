package kr.hhplus.be.server.application.payment.dto.request;

import kr.hhplus.be.server.domain.payment.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class PaymentFacadeRequest {
    private Long userId;
    private Long orderId;
    private Long couponId;
    private PaymentType paymentType;


}
