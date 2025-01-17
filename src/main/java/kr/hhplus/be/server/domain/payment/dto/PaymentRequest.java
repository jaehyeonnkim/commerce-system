package kr.hhplus.be.server.domain.payment.dto;

import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import kr.hhplus.be.server.application.payment.dto.request.PaymentFacadeRequest;
import kr.hhplus.be.server.domain.payment.model.PaymentType;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long userId;
    private Long orderId;
    private Long couponId;
    private PaymentType paymentType;


    public PaymentFacadeRequest toPaymentFacadeRequest(){
        return PaymentFacadeRequest.builder()
                .userId(userId)
                .orderId(orderId)
                .couponId(couponId)
                .paymentType(paymentType)
                .build();
    }
}