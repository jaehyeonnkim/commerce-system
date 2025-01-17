package kr.hhplus.be.server.api.payment.dto;

import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.payment.model.PaymentStatus;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class PaymentResult {
    private PaymentStatus status;
    private int totalAmount;

    public static PaymentResult toResponse(PaymentFacadeResponse response) {
        return PaymentResult.builder()
                .status(response.getStatus())
                .totalAmount(response.getTotalAmount())
                .build();
    }
}
